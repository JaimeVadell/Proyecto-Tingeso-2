package com.microservice.gestorAcademico.Services;

import com.microservice.gestorAcademico.Entities.Prueba;
import com.microservice.gestorAcademico.Model.*;
import com.microservice.gestorAcademico.Utils.BuscadorCuotas;
import com.microservice.gestorAcademico.Utils.BuscadorEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ActualizarPreciosService {


    @Autowired
    BuscadorEstudiante buscadorEstudiante;

    @Autowired
    BuscadorCuotas buscadorCuotas;

    @Autowired
    PruebaService pruebaService;

    @Autowired
    ReembolsoService reembolsoService;


    public void reCalcularArancel(LocalDate fechaActual){
        List<Estudiante> estudiantes = buscadorEstudiante.obtenerTodosEstudiantes();
        for(Estudiante estudiante: estudiantes) {
            Arancel arancel = buscadorCuotas.obtenerArancelPorRut(estudiante.getRut());
            Deuda deudaEstudiante = buscadorCuotas.obtenerDeudaEstudiante(estudiante.getRut());
            if(!arancel.isEstadoDePagoArancel()) {
                List<Cuota> cuotasEstudiante = buscadorCuotas.obtenerCuotasEstudiante(estudiante.getRut()); // Cuotas Ordenadas por fecha de vencimiento
                Optional<Cuota> cuotaMasCercana = cuotasEstudiante.stream()
                        .filter(cuota -> !cuota.isPagada())
                        .findFirst();
                if (cuotaMasCercana.isEmpty()) {
                    continue;
                }

                LocalDate fechaVencimiento = cuotaMasCercana.get().getPlazoMaximoPago();
                if(fechaActual.isAfter(fechaVencimiento)){
                    int diferenciaEnDias = (int) ChronoUnit.DAYS.between(fechaVencimiento,fechaActual);
                    int mesesDeRetrasoActual = (diferenciaEnDias / 30) +1;
                    int mesesDeRetrasoSistema = deudaEstudiante.getCuotasConRetraso();
                    if(mesesDeRetrasoSistema >= mesesDeRetrasoActual){
                        continue;
                    }
                    if(mesesDeRetrasoActual == 1){actuliazarDeudasyCuotas(3, deudaEstudiante, cuotasEstudiante);}
                    else if(mesesDeRetrasoActual == 2){actuliazarDeudasyCuotas(6, deudaEstudiante, cuotasEstudiante);}
                    else if(mesesDeRetrasoActual == 3){actuliazarDeudasyCuotas(9, deudaEstudiante, cuotasEstudiante);}
                    else{
                        actuliazarDeudasyCuotas(15, deudaEstudiante, cuotasEstudiante);
                    }



                }
            }
        }
    }

    private void actuliazarDeudasyCuotas(int interes, Deuda deuda, List<Cuota> cuotas){

        int nuevoPrecioCuota = deuda.getPrecioCuota() + ((deuda.getPrecioCuota() * interes) / 100);
        deuda.setPrecioCuota(nuevoPrecioCuota);
        deuda.setMontoDeuda(deuda.getCuotasRestantes() * nuevoPrecioCuota);
        deuda.setCuotasConRetraso(deuda.getCuotasConRetraso() + 1);
        deuda.setCuotasConRetrasoHistorico(deuda.getCuotasConRetrasoHistorico() + 1);
        Deuda nuevaDeuda = buscadorCuotas.actualizarDeudaEstudiante(deuda.getIdDeuda(),deuda);
        if (nuevaDeuda == null){
            throw new IllegalArgumentException("Error al actualizar deuda");
        }


        // Actuliazar cuotas

        for(Cuota cuota: cuotas){
            if(!cuota.isPagada()){
                cuota.setMontoCuota(nuevoPrecioCuota);
                buscadorCuotas.actualizarCuotaEstudiante(cuota.getIdCuota(), cuota);
            }
        }

    }

    public void actualizarDescuentosPruebaEstudiante(){

        List <Estudiante> estudiantes = buscadorEstudiante.obtenerTodosEstudiantes();
        for (Estudiante estudiante: estudiantes){
            String rutEstudiante = estudiante.getRut();
            List<Prueba> pruebasEstudiante = pruebaService.obtenerPruebasEstudiante(rutEstudiante);
            if(pruebasEstudiante.isEmpty()){
                continue;
            }
            int puntajeTotalAcumulado = 0;
            for (Prueba prueba: pruebasEstudiante){
                puntajeTotalAcumulado += prueba.getPuntaje();
            }
            int promedioPuntajePruebas = Math.round(((float) puntajeTotalAcumulado)/ pruebasEstudiante.size());
            //Setear descuento en base al puntaje
            if (promedioPuntajePruebas >= 950){
                setDescuentoPruebas(rutEstudiante, 10); // Si el puntaje entre 950 y 1000, 10% de descuento
            }
            else if (promedioPuntajePruebas >= 900){
                setDescuentoPruebas(rutEstudiante, 5); // Si el puntaje entre 900 y 949, 5% de descuento
            }
            else if(promedioPuntajePruebas >= 850){
                setDescuentoPruebas(rutEstudiante, 2); //Si el puntaje entre 850 y 899, 2% de descuento
            }
        }
    }


    public void setDescuentoPruebas(String rutEstudiante, int descuento){
        Arancel arancel = buscadorCuotas.obtenerArancelPorRut(rutEstudiante);
        if (arancel == null){
            throw new IllegalArgumentException("Error: Arancel no encontrado - Rut: " + rutEstudiante);
        }
        if(arancel.getPago() == EMedioPago.CONTADO){
            reembolsoService.anadirActualizarReembolsoEstudiante(rutEstudiante, arancel.getMontoTotalArancel() * descuento / 100);
            return;
        }
        List<Cuota> cuotasEstudiante = buscadorCuotas.obtenerCuotasEstudiante(rutEstudiante);
        int montoNuevoDeuda = 0;
        for(Cuota cuota: cuotasEstudiante){
            if(!cuota.isPagada()){
                cuota.setMontoCuota(cuota.getMontoCuota() - ((cuota.getMontoCuota() * descuento) / 100));
                montoNuevoDeuda += cuota.getMontoCuota();
                buscadorCuotas.actualizarCuotaEstudiante(cuota.getIdCuota(), cuota);
            }
        }
        Deuda deuda = buscadorCuotas.obtenerDeudaEstudiante(rutEstudiante);
        deuda.setMontoDeuda(montoNuevoDeuda);
        buscadorCuotas.actualizarDeudaEstudiante(deuda.getIdDeuda(), deuda);

    }
}

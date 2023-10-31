package com.microservice.gestorAcademico.Services;

import com.microservice.gestorAcademico.Model.Arancel;
import com.microservice.gestorAcademico.Model.Cuota;
import com.microservice.gestorAcademico.Model.Deuda;
import com.microservice.gestorAcademico.Model.Estudiante;
import com.microservice.gestorAcademico.Utils.BuscadorCuotas;
import com.microservice.gestorAcademico.Utils.BuscadorEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ActualizarPreciosService {


    @Autowired
    BuscadorEstudiante buscadorEstudiante;

    @Autowired
    BuscadorCuotas buscadorCuotas;

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
                    if(mesesDeRetrasoActual == 1){actuliazarDeudasyCuotas(estudiante, 3);}
                    else if(mesesDeRetrasoActual == 2){actuliazarDeudasyCuotas(estudiante, 6);}
                    else if(mesesDeRetrasoActual == 3){actuliazarDeudasyCuotas(estudiante, 9);}
                    else{
                        actuliazarDeudasyCuotas(estudiante, 15);
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
        buscadorCuotas.actualizarDeuda(deuda);

        // Actuliazar cuotas

        for(Cuota cuota: cuotas){
            if(!cuota.isPagada()){
                cuota.setMontoCuota(nuevoPrecioCuota);
                buscadorCuotas.actualizarCuota(cuota);
            }
        }

    }

    public void actualizarDescuentosPruebaEstudiante(){
        List <Estudiante> estudiantes = estudianteRepository.findAll();
        for (Estudiante estudiante: estudiantes){
            if(estudiante.getPruebas().isEmpty()){
                continue;
            }
            List<Prueba> pruebasEstudiante = estudiante.getPruebas();
            int puntajeTotalAcumulado = 0;
            for (Prueba prueba: pruebasEstudiante){
                puntajeTotalAcumulado += prueba.getPuntaje();
            }
            int promedioPuntajePruebas = Math.round(((float) puntajeTotalAcumulado)/ pruebasEstudiante.size());
            // Si el puntaje entre 950 y 1000, 10% de descuento
            // Si el puntaje entre 900 y 949, 5% de descuento
            //Si el puntaje entre 850 y 899, 2% de descuento
            if(promedioPuntajePruebas <= 849){
                continue;
            }
            else if (promedioPuntajePruebas >= 950){
                setDescuentoPruebas(estudiante, 10);
            }
            else if (promedioPuntajePruebas >= 900){
                setDescuentoPruebas(estudiante, 5);
            }
            //Puntaje entre 850 y 899
            else{
                setDescuentoPruebas(estudiante, 2);
            }

        }
    }


    public void setDescuentoPruebas(Estudiante estudiante, int descuento){
        if (estudiante.getArancel() == null){
            return;
        }
        if(estudiante.getArancel().getPago() == EMedioPago.CONTADO){
            reembolsoService.anadirActualizarReembolsoEstudiante(estudiante, estudiante.getArancel().getMontoTotalArancel() * descuento / 100);
            return;
        }
        List<Cuota> cuotasEstudiante = estudiante.getCuotas();
        int montoNuevoDeuda = 0;
        for(Cuota cuota: cuotasEstudiante){
            if(!cuota.isPagada()){
                cuota.setMontoCuota(cuota.getMontoCuota() - ((cuota.getMontoCuota() * descuento) / 100));
                montoNuevoDeuda += cuota.getMontoCuota();
                cuotasRepository.save(cuota);
            }
        }
        Deuda deuda = estudiante.getDeuda();
        deuda.setMontoDeuda(montoNuevoDeuda);
        deudaRepository.save(deuda);

    }
}

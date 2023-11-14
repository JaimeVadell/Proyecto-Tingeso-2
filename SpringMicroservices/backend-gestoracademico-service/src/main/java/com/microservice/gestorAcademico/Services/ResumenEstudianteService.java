package com.microservice.gestorAcademico.Services;

import com.microservice.gestorAcademico.DTO.ResumenDTO;
import com.microservice.gestorAcademico.Entities.Prueba;
import com.microservice.gestorAcademico.Model.*;
import com.microservice.gestorAcademico.Utils.BuscadorCuotas;
import com.microservice.gestorAcademico.Utils.BuscadorEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ResumenEstudianteService {
    @Autowired
    PruebaService pruebaService;
    @Autowired
    BuscadorEstudiante buscadorEstudiante;
    @Autowired
    BuscadorCuotas buscadorCuotas;

    public ResumenDTO obtenerResumenEstudiante(String rutEstudiante){
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if(estudiante == null){
            throw new IllegalArgumentException("Estudiante no encontrado");
        }
        Arancel arancel = buscadorCuotas.obtenerArancelPorRut(estudiante.getRut());
        if (arancel == null){
            throw new IllegalArgumentException("Arancel no encontrado");
        }
        Deuda deudaEstudiante;
        if(arancel.getPago() == EMedioPago.CONTADO){
            deudaEstudiante = null;
        }
        else{
            deudaEstudiante = buscadorCuotas.obtenerDeudaEstudiante(estudiante.getRut());
        }
        List<Pago> pagosEstudiante = buscadorCuotas.obtenerPagosEstudiante(estudiante.getRut());
        List<Prueba> pruebasEstudiante = pruebaService.obtenerPruebasEstudiante(estudiante.getRut());

        //Calculos Prueba
        int numeroExamenesRendidos = pruebasEstudiante.size();
        float promedioPuntajePruebas;
        if(numeroExamenesRendidos != 0){
            float sumaPuntajePruebas = 0;
            for (Prueba prueba : pruebasEstudiante) {
                sumaPuntajePruebas += prueba.getPuntaje();
            }
            promedioPuntajePruebas = Math.round(((float) sumaPuntajePruebas) / numeroExamenesRendidos);
        }
        else{
            promedioPuntajePruebas = -1;
        }

        //Calculos de Pagos y Cuotas
        int numeroCuotasPagadas = 0;
        for (Pago pago : pagosEstudiante) {
            if(pago.getTipoPago().equals(ETipoPago.CUOTA_ARANCEL)){
                numeroCuotasPagadas++;
            }
        }

        int montoTotalPagado =0;
        for (Pago pago : pagosEstudiante) {
            if(pago.getTipoPago().equals(ETipoPago.CUOTA_ARANCEL)){
                montoTotalPagado += pago.getMontoPagado();
            }
        }
        LocalDate fechaUltimoPago = null;
        for (Pago pago : pagosEstudiante) {
            if(pago.getTipoPago().equals(ETipoPago.CUOTA_ARANCEL)){
                fechaUltimoPago = pago.getFechaPago();
            }
        }
        int montoPorPagar = 0;
        int cuotasConRetraso = 0;
        if(deudaEstudiante != null){
            montoPorPagar = deudaEstudiante.getMontoDeuda();
            cuotasConRetraso = deudaEstudiante.getCuotasConRetrasoHistorico();
        }
        //Creacion de ResumenDTO
        return ResumenDTO.builder()
                .rut(estudiante.getRut())
                .nombre(estudiante.getNombre())
                .apellido(estudiante.getApellido())
                .numeroExamenesRendidos(numeroExamenesRendidos)
                .promedioExamenesRendidos(promedioPuntajePruebas)
                .montoTotalArancel(arancel.getMontoTotalArancel())
                .tipoPago(arancel.getPago())
                .numeroCuotasPactadas(arancel.getNumeroCuotas())
                .numeroCuotasPagadas(numeroCuotasPagadas)
                .montoTotalPagado(montoTotalPagado)
                .fechaUltimoPago(fechaUltimoPago)
                .montoPorPagar(montoPorPagar)
                .numeroCuotasConRetraso(cuotasConRetraso)
                .build();
    }
}

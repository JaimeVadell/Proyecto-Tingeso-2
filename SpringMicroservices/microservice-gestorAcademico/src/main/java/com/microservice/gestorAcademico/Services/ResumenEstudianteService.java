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
        List<Pago> pagosEstudiante = buscadorCuotas.obtenerPagosEstudiante(estudiante.getRut());
        Deuda deudaEstudiante = buscadorCuotas.obtenerDeudaEstudiante(estudiante.getRut());
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
                .montoPorPagar(deudaEstudiante.getMontoDeuda())
                .numeroCuotasConRetraso(deudaEstudiante.getCuotasConRetrasoHistorico())
                .build();
    }
}

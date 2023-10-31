package com.microservice.cuotas.Services;

import com.microservice.cuotas.Entities.Arancel;
import com.microservice.cuotas.Entities.EMedioPago;
import com.microservice.cuotas.Entities.ETipoPago;
import com.microservice.cuotas.Entities.Pago;
import com.microservice.cuotas.Model.ETipoColegio;
import com.microservice.cuotas.Model.Estudiante;
import com.microservice.cuotas.Repositories.ArancelRepository;
import com.microservice.cuotas.Repositories.PagoRepository;
import com.microservice.cuotas.Utils.BuscadorEstudiante;
import com.microservice.cuotas.Utils.VerificadorRut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ArancelService {

    @Autowired
    ArancelRepository arancelRepository;
    @Autowired
    PagoService pagoService;
    @Autowired
    DeudaService deudaService;
    @Autowired
    CuotaService cuotaService;
    @Autowired
    BuscadorEstudiante buscadorEstudiante;

    public Arancel buscarArancelPorId(long id){
        return arancelRepository.findById(id).orElse(null);
    }

    public Arancel buscarArancelPorRut(String rut){
        rut = VerificadorRut.devolverRutParseado(rut);
        if (rut.isEmpty()){
            return null;
        }
        return arancelRepository.findByRutEstudiante(rut).orElse(null);
    }

    public boolean eliminarArancelPorId(long id){
        Arancel arancel = buscarArancelPorId(id);
        if (arancel == null){
            return false;
        }
        arancelRepository.delete(arancel);
        return true;
    }

    public Arancel crearArancelEstudiante(String rutEstudiante, EMedioPago pago, int numeroCuotas){
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if (estudiante == null){
            return null; // no existe estudiante
        }
        if(arancelRepository.findByRutEstudiante(rutEstudiante).isPresent()){
            return null; // ya existe un arancel para ese estudiante
        }
        rutEstudiante = estudiante.getRut();

        // Logica Codigo Anterior
        ETipoColegio tipoColegio = estudiante.getTipoColegio();

        LocalDate anioEgreso = estudiante.getAnioEgreso();
        LocalDate fechaActual = LocalDate.now();
        long diferenciaEnDias = ChronoUnit.DAYS.between(anioEgreso, fechaActual);
        int aniosDesdeEgreso = (int) Math.floor(diferenciaEnDias / 365.25);
        boolean estadoArancel = false;
        int arancelEstudio = 1_500_000;
        int precioMatricula = 70_000;


        // Calculo descuento arancel en base a tipo de colegio para pago en cuotas
        int Descuento = 0;
        if (pago == EMedioPago.CUOTAS) {
            if (tipoColegio == ETipoColegio.MUNICIPAL) {
                Descuento += 20;
            }
            else if (tipoColegio == ETipoColegio.SUBVENCIONADO) {
                if (numeroCuotas > 7) {
                    throw new RuntimeException("Numero de cuotas invalido para colegio SUBVENCIONADO");
                }
                Descuento += 10;
            }
            else if (tipoColegio == ETipoColegio.PRIVADO) {
                if (numeroCuotas > 4) {
                    throw new RuntimeException("Numero de cuotas invalido para colegio PRIVADO");
                }
            }
            //Agregar descuentos en base a anios desde egreso
            if(aniosDesdeEgreso < 1){
                Descuento += 15;
            }
            else if(aniosDesdeEgreso <= 2){
                Descuento += 8;
            }
            else if(aniosDesdeEgreso <=4){
                Descuento += 4;
            }
        }
        //Caso de pago al contado
        else if(pago == EMedioPago.CONTADO){
            Descuento += 50;
            // Pago total del arancel
            Pago pagoContado = Pago.builder()
                    .fechaPago(fechaActual)
                    .montoPagado(arancelEstudio - (arancelEstudio * Descuento / 100))
                    .rutEstudiante(rutEstudiante)
                    .tipoPago(ETipoPago.CUOTA_ARANCEL)
                    .build();
            pagoService.crearPago(rutEstudiante, pagoContado.getMontoPagado(), pagoContado.getFechaPago(), pagoContado.getTipoPago());
            estadoArancel = true;
        }
        //Pago de matricula
        pagarMatricula(rutEstudiante, precioMatricula);

        int precioTotal = arancelEstudio - ((arancelEstudio * Descuento) / 100);
        Arancel arancel = Arancel.builder()
                .estadoDePagoArancel(estadoArancel)
                .fechaCreacionArancel(fechaActual)
                .montoTotalArancel(precioTotal)
                .numeroCuotas(numeroCuotas)
                .pago(pago)
                .rutEstudiante(rutEstudiante)
                .build();
        arancelRepository.save(arancel);
        int deuda = precioTotal;
        if(pago == EMedioPago.CONTADO){
            deuda = 0;
        }
        if(numeroCuotas == 0){
            return arancel;
        }

        deudaService.crearDeuda(rutEstudiante, deuda, numeroCuotas, deuda/numeroCuotas);
        //deudaService.CrearDeudaEstudiante(deuda, numeroCuotas, deuda/numeroCuotas, estudiante);

        cuotaService.generarCuotasArancel(rutEstudiante, deuda/numeroCuotas, numeroCuotas);

        return arancel;
    }

    private void pagarMatricula(String rutEstudiante, int precioMatricula){
        //Falta verificar que sea el unico pago de matricula
        if(pagoService.existePagoMatriculaPorRut(rutEstudiante)){
            throw new IllegalArgumentException("Ya existe un pago de matricula para el estudiante");
        }
        pagoService.crearPago(rutEstudiante, precioMatricula, LocalDate.now(), ETipoPago.MATRICULA);

    }
}

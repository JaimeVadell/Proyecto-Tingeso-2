package com.microservice.cuotas.Services;

import com.microservice.cuotas.Entities.Cuota;
import com.microservice.cuotas.Entities.ETipoPago;
import com.microservice.cuotas.Entities.Pago;
import com.microservice.cuotas.Model.Estudiante;
import com.microservice.cuotas.Repositories.PagoRepository;
import com.microservice.cuotas.Utils.BuscadorEstudiante;
import com.microservice.cuotas.Utils.VerificadorRut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    BuscadorEstudiante buscadorEstudiante;

    @Autowired
    CuotaService cuotaService;
    @Autowired
    DeudaService deudaService;


    public Pago buscarPagoPorId(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public List<Pago> buscarPagosPorRut(String rut) {
        return pagoRepository.findAllByRutEstudianteOrderByFechaPagoAsc(rut);
    }

    public boolean eliminarPagoPorId(Long id){
        Pago pago = pagoRepository.findById(id).orElse(null);
        if (pago == null){
            return false;
        }
        pagoRepository.delete(pago);
        return true;
    }

    public Pago crearPago(String rutEstudiante, int montoPagado, LocalDate fechaPago, ETipoPago tipoPago){
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if (estudiante == null){
            return null; // estudiante no encontrado
        }
        Pago pago = Pago.builder()
                .montoPagado(montoPagado)
                .fechaPago(fechaPago)
                .tipoPago(tipoPago)
                .rutEstudiante(rutEstudiante)
                .build();
        return pagoRepository.save(pago);
    }

    public boolean existePagoMatriculaPorRut(String rutEstudiante){
        rutEstudiante = VerificadorRut.devolverRutParseado(rutEstudiante);
        //Retorna true si existe un pago de matricula para el rut, false si no existe
        return !pagoRepository.findByRutEstudianteAndTipoPago(rutEstudiante, ETipoPago.MATRICULA).isEmpty();
    }

    public Pago pagarProximaCuota(String rutEstudiante) {
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if(estudiante == null){
            throw new IllegalArgumentException("Estudiante no existe");
        }
        List<Cuota> cuotasEstudianteSinPagar = cuotaService.obtenerCuotasSinPagar(estudiante.getRut());
        if (cuotasEstudianteSinPagar.isEmpty()) {
            throw new IllegalArgumentException("Estudiante no tiene cuotas pendientes");
        }
        Cuota cuotaCorrespondiente = cuotasEstudianteSinPagar.get(0);
        // Generar Pago
        Pago pago = Pago.builder()
                .montoPagado(cuotaCorrespondiente.getMontoCuota())
                .fechaPago(LocalDate.now())
                .tipoPago(ETipoPago.CUOTA_ARANCEL)
                .rutEstudiante(estudiante.getRut())
                .build();
        //Guardar Pago
        pagoRepository.save(pago);
        //Actuliazar estado de cuota
        cuotaCorrespondiente.setPagada(true);
        cuotaCorrespondiente.setPago(pago);
        cuotaService.actualizarCuota(cuotaCorrespondiente.getIdCuota(), cuotaCorrespondiente);
        deudaService.actualizardeudaCuotaPago(estudiante.getRut(), cuotaCorrespondiente.getMontoCuota());
        return pago;
    }


}

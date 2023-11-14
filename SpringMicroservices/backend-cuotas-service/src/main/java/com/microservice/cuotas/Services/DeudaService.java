package com.microservice.cuotas.Services;

import com.microservice.cuotas.Entities.Arancel;
import com.microservice.cuotas.Entities.Deuda;
import com.microservice.cuotas.Model.Estudiante;
import com.microservice.cuotas.Repositories.ArancelRepository;
import com.microservice.cuotas.Repositories.DeudaRepository;
import com.microservice.cuotas.Utils.BuscadorEstudiante;
import com.microservice.cuotas.Utils.VerificadorRut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeudaService {
    @Autowired
    DeudaRepository deudaRepository;

    @Autowired
    BuscadorEstudiante buscadorEstudiante;

    @Autowired
    ArancelRepository arancelRepository;

    public Deuda buscarDeudaPorId(Long id) {
        return deudaRepository.findById(id).orElse(null);
    }

    public Deuda buscarDeudaPorRut(String rut) {
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rut);
        if (estudiante == null){
            throw new IllegalArgumentException("Estudiante no encontrado");
        }
        return deudaRepository.findByRutEstudiante(rut);
    }

    public boolean eliminarDeudaPorId(Long id){
        Deuda deuda = deudaRepository.findById(id).orElse(null);
        if (deuda == null){
            return false;
        }
        deudaRepository.delete(deuda);
        return true;
    }

    public Deuda crearDeuda(String rutEstudiante, int montoDeuda, int cuotasRestantes, int precioCuota){
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if (estudiante == null){
            return null; // estudiante no encontrado
        }

        rutEstudiante = estudiante.getRut();
        if (buscarDeudaPorRut(rutEstudiante) != null){
            return null; // ya existe una deuda para este estudiante
        }

        Deuda deuda = Deuda.builder()
                .montoDeuda(montoDeuda)
                .cuotasRestantes(cuotasRestantes)
                .precioCuota(precioCuota)
                .precioCuotaInicial(precioCuota)
                .cuotasConRetraso(0)
                .cuotasConRetrasoHistorico(0)
                .rutEstudiante(rutEstudiante)
                .build();

        return deudaRepository.save(deuda);
    }

    public Deuda actualizarDeudaEstudiante(Long idDeuda, Deuda deuda){
        Deuda deudaActual = buscarDeudaPorId(idDeuda);
        if (deudaActual == null){
            return null; // no existe una deuda con este ID
        }

        deudaActual.setMontoDeuda(deuda.getMontoDeuda());
        deudaActual.setCuotasRestantes(deuda.getCuotasRestantes());
        deudaActual.setPrecioCuota(deuda.getPrecioCuota());
        deudaActual.setPrecioCuotaInicial(deuda.getPrecioCuotaInicial());
        deudaActual.setCuotasConRetraso(deuda.getCuotasConRetraso());
        deudaActual.setCuotasConRetrasoHistorico(deuda.getCuotasConRetrasoHistorico());

        return deudaRepository.save(deudaActual);
    }

    public Deuda actualizardeudaCuotaPago(String rutEstudiante, int montoCuotaPagada){
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if (estudiante == null){
            throw new IllegalArgumentException("Estudiante no encontrado");
        }
        Deuda deuda = buscarDeudaPorRut(estudiante.getRut());
        if (deuda == null){
            throw new IllegalArgumentException("Estudiante no tiene deuda");
        }
        deuda.setCuotasRestantes(deuda.getCuotasRestantes() - 1);
        deuda.setCuotasConRetraso(0); // Si el estudiante paga una cuota, se reinicia el contador de cuotas con retraso
        if (deuda.getCuotasRestantes() == 0){
            Arancel arancel = arancelRepository.findByRutEstudiante(estudiante.getRut()).orElse(null);
            if (arancel == null){
                throw new IllegalArgumentException("Estudiante no tiene arancel");
            }
            arancel.setEstadoDePagoArancel(true);
            arancelRepository.save(arancel);
        }
        deuda.setMontoDeuda(deuda.getMontoDeuda() - montoCuotaPagada);
        return deudaRepository.save(deuda);
    }
}

package com.microservice.cuotas.Services;
import com.microservice.cuotas.Entities.Arancel;
import com.microservice.cuotas.Entities.Cuota;
import com.microservice.cuotas.Model.Estudiante;
import com.microservice.cuotas.Repositories.CuotasRepository;
import com.microservice.cuotas.Utils.BuscadorEstudiante;
import com.microservice.cuotas.Utils.VerificadorRut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class CuotaService {
    @Autowired
    CuotasRepository cuotasRepository;


    @Autowired
    BuscadorEstudiante buscadorEstudiante;

    public Cuota buscarCuotaPorId(Long id) {
        return cuotasRepository.findById(id).orElse(null);
    }

    public List<Cuota> buscarCuotasPorRut(String rut) {
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rut);
        if (estudiante == null){
            throw new IllegalArgumentException("Estudiante no encontrado");
        }

        return cuotasRepository.findAllByRutEstudianteOrderByPlazoMaximoPagoAsc(rut);
    }
    public boolean eliminarCuotaPorId(Long id){
        Cuota cuota = cuotasRepository.findById(id).orElse(null);
        if (cuota == null){
            return false;
        }
        cuotasRepository.delete(cuota);
        return true;
    }

    public Cuota crearCuota(String rutEstudiante, int montoCuota, LocalDate fechaPlazoMaximo){
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if (estudiante == null){
            return null; // estudiante no encontrado
        }

        rutEstudiante = estudiante.getRut();
        Cuota cuota = Cuota.builder()
                .montoCuota(montoCuota)
                .plazoMaximoPago(fechaPlazoMaximo)
                .pagada(false)
                .rutEstudiante(rutEstudiante)
                .build();

        return cuotasRepository.save(cuota);
    }

    public void generarCuotasArancel(String rutEstudiante, int precioCuota, int cuotas) {
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if (estudiante == null){
            throw new IllegalArgumentException("Estudiante no encontrado");
        }

        LocalDate fechaActual = LocalDate.now();
        LocalDate plazoMaximoPago;
        int diaDelMes = fechaActual.getDayOfMonth();

        // Compara si el día del mes actual es mayor que 10
        if (diaDelMes >=5) {
            //Desde el proximo mes
            plazoMaximoPago = LocalDate.of(fechaActual.getYear(), fechaActual.getMonth().plus(1), 10);
        } else {
            //Mes actual
            plazoMaximoPago= LocalDate.of(fechaActual.getYear(), fechaActual.getMonth(), 10);
        }

        for(int i = 0; i < cuotas; i++){
            Cuota cuota = Cuota.builder()
                    .montoCuota(precioCuota)
                    .plazoMaximoPago(plazoMaximoPago)
                    .pagada(false)
                    .rutEstudiante(rutEstudiante)
                    .build();
            cuotasRepository.save(cuota);
            plazoMaximoPago = plazoMaximoPago.plusMonths(1);
            plazoMaximoPago = LocalDate.of(plazoMaximoPago.getYear(), plazoMaximoPago.getMonth(), 10);
        }

    }
}

package com.microservice.gestorAcademico.Services;

import com.microservice.gestorAcademico.Entities.Reembolso;
import com.microservice.gestorAcademico.Model.Estudiante;
import com.microservice.gestorAcademico.Repositories.ReembolsoRepository;
import com.microservice.gestorAcademico.Utils.BuscadorEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReembolsoService {
    @Autowired
    ReembolsoRepository reembolsoRepository;
    @Autowired
    BuscadorEstudiante buscadorEstudiante;

    public Reembolso anadirActualizarReembolsoEstudiante(String rutEstudiante, int montoReembolso ){

        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if (estudiante == null){
            throw new IllegalArgumentException("Estudiante no encontrado");
        }
        rutEstudiante = estudiante.getRut();
        if (reembolsoRepository.findByRutEstudiante(rutEstudiante) != null){
            Reembolso reembolso = reembolsoRepository.findByRutEstudiante(rutEstudiante);
            reembolso.setMontoReembolso(reembolso.getMontoReembolso() + montoReembolso);
            return reembolsoRepository.save(reembolso);
        }
        else {
            Reembolso reembolso = Reembolso.builder()
                    .montoReembolso(montoReembolso)
                    .rutEstudiante(rutEstudiante)
                    .build();
            return reembolsoRepository.save(reembolso);
        }
    }

    public void reclamarReembolso(String rutEstudiante){
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if(estudiante == null){
            throw new IllegalArgumentException("Estudiante no encontrado");
        }
        rutEstudiante = estudiante.getRut();
        Reembolso reembolso = reembolsoRepository.findByRutEstudiante(rutEstudiante);
        reembolso.setMontoReembolso(0);
        reembolsoRepository.save(reembolso);
    }


    public Reembolso obtenerReembolsoRutEstudiante(String rutEstudiante){
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if(estudiante == null){
            throw new IllegalArgumentException("Estudiante no encontrado");
        }
        rutEstudiante = estudiante.getRut();
        return reembolsoRepository.findByRutEstudiante(rutEstudiante);

    }

}

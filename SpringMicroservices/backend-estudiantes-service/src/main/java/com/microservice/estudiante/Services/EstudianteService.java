package com.microservice.estudiante.Services;

import com.microservice.estudiante.Entities.Estudiante;
import com.microservice.estudiante.Repositories.EstudianteRepository;
import com.microservice.estudiante.Utils.VerificadorRut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

    //Operaciones CRUD
    public Optional<Estudiante> buscarEstudianteRut(String rutEstudiante){
        String rutEstudianteParseado = VerificadorRut.devolverRutParseado(rutEstudiante);
        if (rutEstudianteParseado.isEmpty()){
            return Optional.empty();
        }
        return estudianteRepository.findById(rutEstudianteParseado);
    }

    public Estudiante crearEstudiante(Estudiante estudiante){
        String rutEstudianteParseado = VerificadorRut.devolverRutParseado(estudiante.getRut());
        if(estudianteRepository.existsById(rutEstudianteParseado) || rutEstudianteParseado.isEmpty()){
            return null;
        }
        estudiante.setRut(rutEstudianteParseado);
        return estudianteRepository.save(estudiante);
    }

    public Estudiante actualizarEstudiante(Estudiante estudiante){
        String rutEstudianteParseado = VerificadorRut.devolverRutParseado(estudiante.getRut());
        if(rutEstudianteParseado.isEmpty() || !estudianteRepository.existsById(rutEstudianteParseado)){
            return null;
        }
        estudiante.setRut(rutEstudianteParseado);
        return estudianteRepository.save(estudiante);
    }

    public boolean eliminarEstudiante(Estudiante estudiante){
        String rutEstudianteParseado = VerificadorRut.devolverRutParseado(estudiante.getRut());
        if(!estudianteRepository.existsById(rutEstudianteParseado) || rutEstudianteParseado.isEmpty()){
            return false;
        }
        estudianteRepository.delete(estudiante);
        return true;
    }


    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }
}

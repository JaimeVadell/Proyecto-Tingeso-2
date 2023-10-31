package com.microservice.gestorAcademico.Utils;

import com.microservice.gestorAcademico.Model.Estudiante;
import jakarta.ws.rs.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BuscadorEstudiante {

    @Autowired
    RestTemplate restTemplate;

    public Estudiante buscarEstudiantePorRut(String rut){
        rut = VerificadorRut.devolverRutParseado(rut);
        if (rut.isEmpty()){
            return null; // rut invalido
        }
        return restTemplate.getForObject("http://estudiante-service/estudiante/" + rut, Estudiante.class);
    }

    public List<Estudiante> obtenerTodosEstudiantes(){
        return restTemplate.getForObject("http://estudiante-service/estudiante/", List.class);
    }

}

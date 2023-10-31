package com.microservice.gestorAcademico.Utils;

import com.microservice.gestorAcademico.Model.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

}

package com.microservice.gestorAcademico.Utils;

import com.microservice.gestorAcademico.Model.Estudiante;
import com.microservice.gestorAcademico.Model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        String url = "http://estudiante-service/estudiante";
        ParameterizedTypeReference<List<Estudiante>> responseType = new ParameterizedTypeReference<List<Estudiante>>() {};
        ResponseEntity<List<Estudiante>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return responseEntity.getBody();
    }

}

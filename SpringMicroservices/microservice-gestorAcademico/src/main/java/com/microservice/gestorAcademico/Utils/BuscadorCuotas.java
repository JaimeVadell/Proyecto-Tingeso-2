package com.microservice.gestorAcademico.Utils;

import com.microservice.gestorAcademico.Model.Arancel;
import com.microservice.gestorAcademico.Model.Cuota;
import com.microservice.gestorAcademico.Model.Deuda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Component
public class BuscadorCuotas {
    @Autowired
    RestTemplate restTemplate;

    public Arancel obtenerArancelPorRut(String rutEstudiante){
        String url = "http://cuotas-service/arancel/" + rutEstudiante;
        return restTemplate.getForObject(url, Arancel.class);
    }

    public List<Cuota> obtenerCuotasEstudiante(String rutEstudiante){
        String url = "http://cuotas-service/cuotas/" + rutEstudiante;
         return restTemplate.getForObject(url, List.class);
    }

    public Deuda obtenerDeudaEstudiante(String rutEstudiante){
        String url = "http://cuotas-service/deuda/" + rutEstudiante;
        return restTemplate.getForObject(url, Deuda.class);
    }

    //Probar Metodo
    public Deuda actualizarDeudaEstudiante(Long idDeuda, Deuda deuda){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Deuda> requestEntity = new HttpEntity<>(deuda, headers);

        String url = "http://cuotas-service/deuda/" + idDeuda;

        ResponseEntity<Deuda> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Deuda.class);

        return response.getBody();
    }

    public Cuota actualizarCuotaEstudiante(Long idCuota, Cuota cuota){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Cuota> requestEntity = new HttpEntity<>(cuota, headers);

        String url = "http://cuotas-service/cuotas/actualizar/" + idCuota;

        ResponseEntity<Cuota> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Cuota.class);

        return response.getBody();
    }
}

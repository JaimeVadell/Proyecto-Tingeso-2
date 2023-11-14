package com.microservice.gestorAcademico.Utils;

import com.microservice.gestorAcademico.Model.Arancel;
import com.microservice.gestorAcademico.Model.Cuota;
import com.microservice.gestorAcademico.Model.Deuda;
import com.microservice.gestorAcademico.Model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
        String url = "http://backend-cuotas-service/arancel/" + rutEstudiante;
        return restTemplate.getForObject(url, Arancel.class);
    }

    public List<Cuota> obtenerCuotasEstudiante(String rutEstudiante){
        String url = "http://backend-cuotas-service/cuotas/" + rutEstudiante;
        ParameterizedTypeReference<List<Cuota>> responseType = new ParameterizedTypeReference<List<Cuota>>() {};
        ResponseEntity<List<Cuota>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return responseEntity.getBody();
    }

    public Deuda obtenerDeudaEstudiante(String rutEstudiante){
        String url = "http://backend-cuotas-service/deuda/" + rutEstudiante;
        return restTemplate.getForObject(url, Deuda.class);
    }

    //Probar Metodo
    public Deuda actualizarDeudaEstudiante(Long idDeuda, Deuda deuda){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Deuda> requestEntity = new HttpEntity<>(deuda, headers);

        String url = "http://backend-cuotas-service/deuda/" + idDeuda;

        ResponseEntity<Deuda> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Deuda.class);

        return response.getBody();
    }

    public Cuota actualizarCuotaEstudiante(Long idCuota, Cuota cuota){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Cuota> requestEntity = new HttpEntity<>(cuota, headers);

        String url = "http://backend-cuotas-service/cuotas/actualizar/" + idCuota;

        ResponseEntity<Cuota> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Cuota.class);

        return response.getBody();
    }

    public List<Pago> obtenerPagosEstudiante(String rut) {
        String url = "http://backend-cuotas-service/pagos/" + rut;
        ParameterizedTypeReference<List<Pago>> responseType = new ParameterizedTypeReference<List<Pago>>() {};

        ResponseEntity<List<Pago>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        // Obtiene la lista de Pago del ResponseEntity

        return responseEntity.getBody();
    }
}

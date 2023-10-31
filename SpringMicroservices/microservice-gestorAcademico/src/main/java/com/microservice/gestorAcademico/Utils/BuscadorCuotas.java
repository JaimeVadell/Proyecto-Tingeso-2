package com.microservice.gestorAcademico.Utils;

import com.microservice.gestorAcademico.Model.Arancel;
import com.microservice.gestorAcademico.Model.Cuota;
import com.microservice.gestorAcademico.Model.Deuda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
}

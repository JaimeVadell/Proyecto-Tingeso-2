package com.microservice.cuotas.Controller;

import com.microservice.cuotas.Entities.Cuota;
import com.microservice.cuotas.Services.CuotaService;
import com.microservice.cuotas.Utils.VerificadorRut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cuotas")
public class CuotasController {

    @Autowired
    CuotaService cuotaService;

    @GetMapping("{rutEstudiante}")
    public ResponseEntity<List<Cuota>> obtenerCuotasEstudiante(@PathVariable("rutEstudiante") String rutEstudiante){
           List<Cuota> cuotasEstudiante = cuotaService.buscarCuotasPorRut(rutEstudiante);
              if (cuotasEstudiante.isEmpty()){
                return ResponseEntity.noContent().build();
              }
              return ResponseEntity.ok(cuotasEstudiante);
    }


}

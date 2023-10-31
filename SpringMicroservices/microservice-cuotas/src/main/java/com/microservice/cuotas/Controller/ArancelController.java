package com.microservice.cuotas.Controller;

import com.microservice.cuotas.Entities.Arancel;
import com.microservice.cuotas.Entities.EMedioPago;
import com.microservice.cuotas.Services.ArancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/arancel")
public class ArancelController {
    @Autowired
    ArancelService arancelService;

    @GetMapping("/{rutEstudiante}")
    public ResponseEntity<Arancel> obtenerArancel(@PathVariable("rutEstudiante") String rutEstudiante){
        Arancel arancel = arancelService.buscarArancelPorRut(rutEstudiante);
        if(arancel== null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(arancel);
    }

    @PostMapping("/crear")
    public ResponseEntity<Arancel> crearArancel(){

        Arancel arancel = arancelService.crearArancelEstudiante("20107536-K", EMedioPago.CUOTAS, 3);
        if(arancel== null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(arancel);
    }
}

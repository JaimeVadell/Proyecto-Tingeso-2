package com.microservice.gestorAcademico.Controller;

import com.microservice.gestorAcademico.Entities.Reembolso;
import com.microservice.gestorAcademico.Services.ReembolsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reembolso")
public class ReembolsoController {
    @Autowired
    ReembolsoService reembolsoService;

    @GetMapping("/{rutEstudiante}")
    public ResponseEntity<Reembolso> obtenerReembolsoEstudiante(@PathVariable("rutEstudiante") String rutEstudiante){
        Reembolso reembolso = reembolsoService.obtenerReembolsoRutEstudiante(rutEstudiante);
        if(reembolso == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reembolso);
    }

    @PostMapping("/{rutEstudiante}")
    public ResponseEntity<Reembolso> reclamarReembolsoEstudiante(@PathVariable("rutEstudiante") String rutEstudiante){
        Reembolso reembolso = reembolsoService.reclamarReembolso(rutEstudiante);
        if(reembolso == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reembolso);
    }
}

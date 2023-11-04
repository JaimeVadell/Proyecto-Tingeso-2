package com.microservice.gestorAcademico.Controller;

import com.microservice.gestorAcademico.DTO.ResumenDTO;
import com.microservice.gestorAcademico.Services.ResumenEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumen-estudiante")
public class ResumenEstudianteController {

    @Autowired
    ResumenEstudianteService resumenEstudianteService;

    @GetMapping("/{rutEstudiante}")
    public ResponseEntity<ResumenDTO> obtenerResumenEstudiante(@PathVariable("rutEstudiante") String rutEstudiante){
        ResumenDTO resumenDTO = resumenEstudianteService.obtenerResumenEstudiante(rutEstudiante);
        if(resumenDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resumenDTO);
    }
}

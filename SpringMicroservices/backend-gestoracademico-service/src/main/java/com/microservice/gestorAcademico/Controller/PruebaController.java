package com.microservice.gestorAcademico.Controller;

import com.microservice.gestorAcademico.Services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/pruebas")
public class PruebaController {

    @Autowired
    PruebaService pruebaService;

    @PostMapping("/guardar-prueba")
    public ResponseEntity<String> subirPrueba(@RequestParam("archivo")MultipartFile archivo){
        if(!archivo.isEmpty()){
            pruebaService.RevisarDocumentoExamen(archivo);
        }
        return ResponseEntity.ok("Prueba guardada");
    }
}

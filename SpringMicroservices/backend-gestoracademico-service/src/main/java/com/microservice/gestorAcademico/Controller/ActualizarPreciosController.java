package com.microservice.gestorAcademico.Controller;

import com.microservice.gestorAcademico.Services.ActualizarPreciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/actualizar-precios")
public class ActualizarPreciosController {

    LocalDate fechaActual = LocalDate.now();

    @Autowired
    ActualizarPreciosService actualizarPreciosService;
    @PostMapping("/aranceles")
    public ResponseEntity<String> actualizarAranceles(@RequestBody LocalDate fechaCalculo){
        if (fechaCalculo.isBefore(LocalDate.now())){
            return ResponseEntity.badRequest().body("La fecha de calculo debe ser posterior a la fecha actual");
        }
        try{
            actualizarPreciosService.reCalcularArancel(fechaCalculo);
            return ResponseEntity.ok("Aranceles actualizados");
    }
        catch (Exception e){
            //show error
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().body("Error al actualizar aranceles");
        }
    }

    @PostMapping("/pruebas")
    public ResponseEntity<String> actualizarPruebas(){
        if (!(LocalDate.now().getYear() == fechaActual.getYear() && LocalDate.now().getMonthValue() == fechaActual.getMonthValue() && LocalDate.now().getDayOfMonth() == fechaActual.getDayOfMonth())){
            return ResponseEntity.badRequest().body("No se puede actualizar las pruebas el mismo dia que se realizan");
        }
        try{

            actualizarPreciosService.actualizarDescuentosPruebaEstudiante();
            fechaActual = LocalDate.now().plusMonths(1);
            return ResponseEntity.ok("Pruebas actualizadas");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error al actualizar pruebas");
        }
    }
}

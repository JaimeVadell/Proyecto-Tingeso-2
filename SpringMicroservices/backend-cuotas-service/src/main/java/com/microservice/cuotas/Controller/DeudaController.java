package com.microservice.cuotas.Controller;

import com.microservice.cuotas.Entities.Deuda;
import com.microservice.cuotas.Services.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deuda")
public class DeudaController {
    @Autowired
    DeudaService deudaService;


    @GetMapping("/{rutEstudiante}")
    public ResponseEntity<Deuda> obtenerDeudaEstudiante(@PathVariable("rutEstudiante") String rutEstudiante) {
        Deuda deuda = deudaService.buscarDeudaPorRut(rutEstudiante);
        if (deuda == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deuda);
    }

    @PutMapping("/{idDeuda}")
    public ResponseEntity<Deuda> actualizarDeudaEstudiante(@PathVariable("idDeuda") Long idDeuda, @RequestBody Deuda deuda) {
        Deuda deudaEstudiante = deudaService.actualizarDeudaEstudiante(idDeuda, deuda);
        if (deudaEstudiante == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(deudaEstudiante);
    }
    

}

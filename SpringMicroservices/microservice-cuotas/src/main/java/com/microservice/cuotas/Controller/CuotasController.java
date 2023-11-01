package com.microservice.cuotas.Controller;

import com.microservice.cuotas.Entities.Cuota;
import com.microservice.cuotas.Services.CuotaService;
import com.microservice.cuotas.Utils.VerificadorRut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/crear/{rutEstudiante}")
    public ResponseEntity<Cuota> crearCuotaEstudiante(@PathVariable String rutEstudiante, @RequestBody Cuota cuota){
        Cuota cuotaEstudiante = cuotaService.crearCuotaSinDatos(rutEstudiante, cuota);
        if(cuotaEstudiante == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cuotaEstudiante);
    }

    @PutMapping("/actualizar/{idCuota}")
    public ResponseEntity<Cuota> actualizarCuotaEstudiante(@PathVariable("idCuota") Long idCuota, @RequestBody Cuota cuota){
        Cuota cuotaActualizada = cuotaService.actualizarCuota(idCuota, cuota);
        if(cuotaActualizada == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cuotaActualizada);
    }




}

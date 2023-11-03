package com.microservice.cuotas.Controller;

import com.microservice.cuotas.DTO.CrearArancelDTO;
import com.microservice.cuotas.Entities.Arancel;
import com.microservice.cuotas.Entities.EMedioPago;
import com.microservice.cuotas.Services.ArancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arancel")
public class ArancelController {
    @Autowired
    ArancelService arancelService;

    @GetMapping
    public ResponseEntity<List<Arancel>> obtenerTodos(){
        List<Arancel> arancel = arancelService.obtenerTodos();
        if(arancel.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(arancel);
    }

    @GetMapping("/{rutEstudiante}")
    public ResponseEntity<Arancel> obtenerArancel(@PathVariable("rutEstudiante") String rutEstudiante){
        Arancel arancel = arancelService.buscarArancelPorRut(rutEstudiante);
        if(arancel== null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(arancel);
    }

    @PostMapping("/crear")
    public ResponseEntity<Arancel> crearArancel(@RequestBody CrearArancelDTO crearArancelDTO){
        Arancel arancel = arancelService.crearArancelEstudiante(crearArancelDTO.getRut()
                ,crearArancelDTO.getEmedioPago(),crearArancelDTO.getNumeroCuotas());

        if(arancel== null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(arancel);
    }
}

package com.microservice.estudiante.Controller;

import com.microservice.estudiante.Entities.Estudiante;
import com.microservice.estudiante.Services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/estudiante")
public class EstudianteController {
    @Autowired
    EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<Estudiante>> obtenerTodos(){
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        if(estudiantes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Estudiante> getById(@PathVariable("rut") String rut){
        Optional<Estudiante> estudiante = estudianteService.buscarEstudianteRut(rut);
        if(estudiante.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estudiante.get());
    }

    @PostMapping()
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody Estudiante estudiante) {
        System.out.println("Estoy en el controller");
        Estudiante estudianteNew = estudianteService.crearEstudiante(estudiante);
        if(estudianteNew == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(estudianteNew);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Estudiante> actualizarEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante estudianteNew = estudianteService.actualizarEstudiante(estudiante);
        if(estudianteNew == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(estudianteNew);
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Estudiante> eliminarEstudiante(@PathVariable("rut") String rut){
        Optional<Estudiante> estudiante = estudianteService.buscarEstudianteRut(rut);
        if(estudiante.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        if(estudianteService.eliminarEstudiante(estudiante.get())){
            return ResponseEntity.ok(estudiante.get());
        }
        return ResponseEntity.badRequest().build();
    }


}

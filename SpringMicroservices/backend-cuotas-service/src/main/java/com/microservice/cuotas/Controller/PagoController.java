package com.microservice.cuotas.Controller;

import com.microservice.cuotas.Entities.Pago;
import com.microservice.cuotas.Services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    PagoService pagoService;

    @GetMapping("/{rutEstudiante}")
    public ResponseEntity<List<Pago>> obtenerPagosEstudiante(@PathVariable("rutEstudiante") String rutEstudiante){
           List<Pago> pagosEstudiante = pagoService.buscarPagosPorRut(rutEstudiante);
              if (pagosEstudiante.isEmpty()){
                return ResponseEntity.noContent().build();
              }
              return ResponseEntity.ok(pagosEstudiante);
    }
    @PostMapping("/pagar-cuota/{rutEstudiante}")
    public ResponseEntity<Pago> pagarProximaCuota(@PathVariable("rutEstudiante") String rutEstudiante){
        Pago pago = pagoService.pagarProximaCuota(rutEstudiante);
        if(pago == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(pago);
    }

}

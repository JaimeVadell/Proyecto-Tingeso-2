package com.microservice.cuotas.DTO;

import com.microservice.cuotas.Entities.EMedioPago;
import lombok.Data;

@Data
public class CrearArancelDTO {
    private String rut;
    private EMedioPago medioPago;
    private int numeroCuotas;

}
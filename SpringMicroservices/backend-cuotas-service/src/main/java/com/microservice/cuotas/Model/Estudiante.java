package com.microservice.cuotas.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Estudiante {

    private String rut;

    private String nombre;

    private String apellido;

    private LocalDate fechaNacimiento;

    private ETipoColegio tipoColegio;

    private String nombreColegio;

    private LocalDate anioEgreso;
}
package com.microservice.estudiante.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="Estudiantes")
public class Estudiante {
    @Id @NotBlank
    private String rut;
    @NotBlank @Size(max = 50)
    private String nombre;
    @NotBlank @Size(max = 50)
    private String apellido;
    @NotNull
    private LocalDate fechaNacimiento;
    @NotNull
    private ETipoColegio tipoColegio;
    @NotBlank @Size(max = 50)
    private String nombreColegio;
    @NotNull
    private LocalDate anioEgreso;
}

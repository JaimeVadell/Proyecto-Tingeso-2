package com.microservice.gestorAcademico.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Pruebas")
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long IdPrueba;

    @NotNull
    @Max(value = 1000, message = "El puntaje máximo permitido es 100")
    @Min(value = 0, message = "El puntaje mínimo permitido es 0")
    private int puntaje;
    @NotNull
    private LocalDate diaPrueba;
    @NotNull
    private String rutEstudiante;
}

package com.microservice.gestorAcademico.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPago;
    @NotNull
    private int montoPagado;
    @NotNull
    private LocalDate fechaPago;
    @NotNull
    private ETipoPago tipoPago;
    @NotNull
    private String rutEstudiante;
}

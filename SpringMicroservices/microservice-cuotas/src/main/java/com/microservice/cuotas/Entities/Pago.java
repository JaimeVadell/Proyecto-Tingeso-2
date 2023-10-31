package com.microservice.cuotas.Entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "Pagos")
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

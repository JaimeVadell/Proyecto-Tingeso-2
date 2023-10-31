package com.microservice.cuotas.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Cuotas")
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCuota;

    @NotNull
    private int montoCuota;

    @NotNull
    private LocalDate plazoMaximoPago;

    @NotNull
    private boolean pagada;

    @OneToOne
    @JoinColumn(name = "idPago") // Nombre de la columna que actuará como clave foránea
    private Pago pago;

    @NotNull
    private String rutEstudiante;
}

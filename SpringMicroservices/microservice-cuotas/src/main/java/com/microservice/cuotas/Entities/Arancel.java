package com.microservice.cuotas.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Matriculas")
public class Arancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idArancel;
    @NotNull
    private EMedioPago pago;
    @NotNull
    private LocalDate fechaCreacionArancel;
    @NotNull
    private int montoTotalArancel;
    @NotNull
    private int numeroCuotas;
    @NotNull
    private boolean estadoDePagoArancel;
    @NotNull
    private String rutEstudiante;
}

package com.microservice.cuotas.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Deudas")
public class Deuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDeuda;
    @NotNull
    private int montoDeuda;
    @NotNull
    private int cuotasRestantes;
    @NotNull
    private int precioCuota;
    @NotNull
    private int precioCuotaInicial;
    @NotNull
    private int cuotasConRetraso;
    @NotNull
    private int cuotasConRetrasoHistorico;
    @NotNull
    private String rutEstudiante;
}

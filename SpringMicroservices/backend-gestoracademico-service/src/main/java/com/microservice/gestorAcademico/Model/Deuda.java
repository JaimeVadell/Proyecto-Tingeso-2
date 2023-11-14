package com.microservice.gestorAcademico.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

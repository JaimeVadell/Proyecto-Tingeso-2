package com.microservice.gestorAcademico.DTO;

import com.microservice.gestorAcademico.Model.EMedioPago;
import com.microservice.gestorAcademico.Model.ETipoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumenDTO {
    private String rut;
    private String nombre;
    private String apellido;
    private int numeroExamenesRendidos;
    private float promedioExamenesRendidos;
    private int montoTotalArancel;
    private EMedioPago tipoPago;
    private int numeroCuotasPactadas;
    private int numeroCuotasPagadas;
    private int montoTotalPagado;
    private LocalDate fechaUltimoPago;
    private int montoPorPagar;
    private int numeroCuotasConRetraso;
}

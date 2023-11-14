package com.microservice.cuotas.Repositories;

import com.microservice.cuotas.Entities.ETipoPago;
import com.microservice.cuotas.Entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findAllByRutEstudianteOrderByFechaPagoAsc(String rutEstudiante);

    List<Pago> findByRutEstudianteAndTipoPago(String rutEstudiante, ETipoPago tipoPago);
}
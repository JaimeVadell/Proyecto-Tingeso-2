package com.microservice.cuotas.Repositories;

import com.microservice.cuotas.Entities.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuotasRepository extends JpaRepository<Cuota, Long> {

    List<Cuota> findAllByRutEstudianteOrderByPlazoMaximoPagoAsc(String rutEstudiante);

}

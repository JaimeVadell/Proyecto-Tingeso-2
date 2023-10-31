package com.microservice.cuotas.Repositories;


import com.microservice.cuotas.Entities.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Long> {
     Deuda findByRutEstudiante(String rut);
}

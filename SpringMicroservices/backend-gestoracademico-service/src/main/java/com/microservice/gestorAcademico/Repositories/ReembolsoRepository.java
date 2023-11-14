package com.microservice.gestorAcademico.Repositories;

import com.microservice.gestorAcademico.Entities.Reembolso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReembolsoRepository extends JpaRepository<Reembolso, Long> {

    Reembolso findByRutEstudiante(String rutEstudiante);

}

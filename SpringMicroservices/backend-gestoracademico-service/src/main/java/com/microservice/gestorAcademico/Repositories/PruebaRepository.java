package com.microservice.gestorAcademico.Repositories;

import com.microservice.gestorAcademico.Entities.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Long> {

    List<Prueba> findAllByRutEstudianteOrderByDiaPruebaAsc(String rutEstudiante);
}

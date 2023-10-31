package com.microservice.cuotas.Repositories;

import com.microservice.cuotas.Entities.Arancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArancelRepository extends JpaRepository<Arancel, Long> {


    Optional<Arancel> findByRutEstudiante(String rutEstudiante);
}
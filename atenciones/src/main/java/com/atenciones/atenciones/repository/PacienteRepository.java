package com.atenciones.atenciones.repository;

import com.atenciones.atenciones.model.Paciente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PacienteRepository extends JpaRepository<Paciente, Long>{

     Optional<Paciente> findByRut(String rut);
}

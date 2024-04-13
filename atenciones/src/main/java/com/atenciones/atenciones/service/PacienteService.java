package com.atenciones.atenciones.service;

import com.atenciones.atenciones.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    List<Paciente> getAllPaciente();
    Optional<Paciente> getPacienteByid(Long id);
    Optional<Paciente> findByRut(String rut);

    Paciente createPaciente(Paciente paciente);

    Paciente updatePaciente (Long id, Paciente paciente);

    void deletePaciente (Long id);


}

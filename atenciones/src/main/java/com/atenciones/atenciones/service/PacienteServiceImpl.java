package com.atenciones.atenciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atenciones.atenciones.model.Paciente;
import com.atenciones.atenciones.repository.PacienteRepository;

import java.util.List;
import java.util.Optional;


@Service
public class PacienteServiceImpl implements PacienteService{
    @Autowired
    private PacienteRepository pacienteRepository;
    @Override
    public List<Paciente> getAllPaciente() {
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> getPacienteByid(Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Paciente createPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente updatePaciente(Long id, Paciente paciente) {
        if( pacienteRepository.existsById(id)){
            paciente.setId(id);
            return pacienteRepository.save(paciente);
        }else{
            return null;
        }
    }
    @Override
    public void deletePaciente(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public Optional<Paciente> findByRut(String rut) {

        return pacienteRepository.findByRut(rut);
    }

}

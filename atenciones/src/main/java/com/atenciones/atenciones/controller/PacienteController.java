package com.atenciones.atenciones.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atenciones.atenciones.model.Paciente;
import com.atenciones.atenciones.service.PacienteService;

import java.util.List;

import java.util.Optional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;

    

    @GetMapping
    public List<Paciente> getAllPaciente(){
        return pacienteService.getAllPaciente();
    }
            
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPacienteByid(@PathVariable Long id) {
        Optional<Paciente> pacienteOptional = pacienteService.getPacienteByid(id);
        if (pacienteOptional.isPresent()) {
            return ResponseEntity.ok(pacienteOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún paciente con el ID especificado");
        }
    }


    @PostMapping("/crear")
    public ResponseEntity<Object> createUsuario(@Valid @RequestBody Paciente paciente, BindingResult result) {
        String rut = paciente.getRut();
        Optional<Paciente> existingPaciente = pacienteService.findByRut(rut);
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error de validación: ");
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }
        if (existingPaciente.isPresent()) {

            StringBuilder errorMessage = new StringBuilder("Ya existe un paciente con el rut: " + rut);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }
        Paciente createdPaciente = pacienteService.createPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable Long id, @Valid @RequestBody Paciente paciente, BindingResult result) {

        Optional<Paciente> pacienteOptional = pacienteService.getPacienteByid(id);
        if (pacienteOptional.isPresent()) {
            if (result.hasErrors()) {
                // Si hay errores de validación, construye una respuesta con los mensajes de error
                StringBuilder errorMessage = new StringBuilder("Error de validación: ");
                result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
            }
            else{
                Paciente updatedPaciente = pacienteService.updatePaciente(id, paciente);
                return ResponseEntity.ok(updatedPaciente);
            }      
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún paciente con el ID especificado");
        }
    }
    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable Long id) {
        pacienteService.deletePaciente(id);
    } 
}

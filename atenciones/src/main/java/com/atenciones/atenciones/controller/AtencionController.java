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

import com.atenciones.atenciones.model.Atencion;
import com.atenciones.atenciones.model.Paciente;
import com.atenciones.atenciones.service.AtencionService;
import com.atenciones.atenciones.service.PacienteService;

import java.util.List;

import java.util.Optional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/atenciones")
public class AtencionController {
    
    @Autowired
    private AtencionService atencionService;
    @Autowired
    private PacienteService pacienteService;
    @GetMapping
    public List<Atencion> getAllAtencion(){
        return atencionService.getAllAtencion();
    }
            
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPacienteByid(@PathVariable Long id) {
        Optional<Atencion> atencionExist = atencionService.getAtencionByid(id);
        if (atencionExist.isPresent()) {
            return ResponseEntity.ok(atencionExist.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún atencion con el ID especificado");
        }
    }
    @PostMapping("/crear")
    public ResponseEntity<Object> createAtencion(@Valid @RequestBody Atencion atencion, BindingResult result) {
        // Verificar si hay errores de validación en la atención
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error de validación: ");
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }
        // Obtener el rut del paciente asociado a la atención
        String rutPaciente = atencion.getRut_paciente();
        // Obtener el paciente asociado al rut proporcionado
        Optional<Paciente> pacienteOptional = pacienteService.findByRut(rutPaciente);
        // Verificar si el paciente existe
        if (pacienteOptional.isEmpty()) {
            String errorMessage = "No se encontró ningún paciente con el rut: " + rutPaciente;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else {
            // Asignar el paciente a la atención
            Atencion createdAtencion = atencionService.createAtencion(atencion);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAtencion);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable Long id, @Valid @RequestBody Atencion atencion, BindingResult result) {

        Optional<Atencion> atencionExits = atencionService.getAtencionByid(id);
        if (atencionExits.isPresent()) {
                // Obtener el rut del paciente asociado a la atención
                String rutPaciente = atencion.getRut_paciente();
                // Obtener el paciente asociado al rut proporcionado
                Optional<Paciente> pacienteOptional = pacienteService.findByRut(rutPaciente);
            if (result.hasErrors()) {
                // Si hay errores de validación, construye una respuesta con los mensajes de error
                StringBuilder errorMessage = new StringBuilder("Error de validación: ");
                result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
            }
            if(pacienteOptional.isPresent()){
                Atencion updatedAtencion = atencionService.updateAtencion(id, atencion);
                return ResponseEntity.ok(updatedAtencion);
            }
            else
            {
                String errorMessage = "No se encontró ningún paciente con el rut: " + rutPaciente;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún paciente con el ID especificado");
        }
    }
    @DeleteMapping("/{id}")
    public void deleteAtencion(@PathVariable Long id) {
        atencionService.deleteAtencion(id);
    } 
}

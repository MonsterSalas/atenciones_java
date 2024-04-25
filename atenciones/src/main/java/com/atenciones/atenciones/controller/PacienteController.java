package com.atenciones.atenciones.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import java.util.stream.Collectors;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public CollectionModel<EntityModel<Paciente>> getAllPaciente(){
        List<Paciente> pacientes = pacienteService.getAllPaciente();
        List<EntityModel<Paciente>> pacienteResources = pacientes.stream()
            .map(paciente-> EntityModel.of(paciente,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteByid(paciente.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPaciente());
        CollectionModel<EntityModel<Paciente>> resources = CollectionModel.of(pacienteResources, linkTo.withRel("paciente"));
        return resources;
    }
            
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPacienteByid(@PathVariable Long id) {
        Optional<Paciente> pacienteOptional = pacienteService.getPacienteByid(id);
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            EntityModel<Paciente> entityModel = EntityModel.of(paciente,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteByid(id)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPaciente()).withRel("all-pacientes"));
            return ResponseEntity.ok(entityModel);
        } else {
            ResponseMessage errorResponse = new ResponseMessage("No se encontró ningún paciente con el ID especificado");
            EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityModel);
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<Object> createUsuario(@Valid @RequestBody Paciente paciente, BindingResult result) {
        String rut = paciente.getRut();
        Optional<Paciente> existingPaciente = pacienteService.findByRut(rut);
        if (result.hasErrors()) {
            // Si hay errores de validación, construye una respuesta con los mensajes de error
            StringBuilder errorMessage = new StringBuilder("Error de validación: ");
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
            ResponseMessage errorResponse = new ResponseMessage(errorMessage.toString());
            EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).createUsuario(paciente, result)).withSelfRel()));
        }
        if (existingPaciente.isPresent()) {
            // Si ya existe un usuario con el mismo rut, construye una respuesta con el mensaje de error
            ResponseMessage errorResponse = new ResponseMessage("Ya existe un paciente con el rut: " + rut);
            EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).createUsuario(paciente, result)).withSelfRel()));
        }
        Paciente createdPaciente = pacienteService.createPaciente(paciente);
        EntityModel<Paciente> entityModel = EntityModel.of(createdPaciente,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteByid(createdPaciente.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPaciente()).withRel("all-pacientes"));
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable Long id, @Valid @RequestBody Paciente paciente, BindingResult result) {
        Optional<Paciente> pacienteOptional = pacienteService.getPacienteByid(id);
        if (pacienteOptional.isPresent()) {
            if (result.hasErrors()) {
                // Si hay errores de validación, construye una respuesta con los mensajes de error
                StringBuilder errorMessage = new StringBuilder("Error de validación: ");
                result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
                ResponseMessage errorResponse = new ResponseMessage(errorMessage.toString());
                EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateUsuario(id, paciente, result)).withSelfRel()));
            }
            Paciente updatedPaciente = pacienteService.updatePaciente(id, paciente);
            EntityModel<Paciente> entityModel = EntityModel.of(updatedPaciente,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteByid(updatedPaciente.getId())).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPaciente()).withRel("all-pacientes"));
            return ResponseEntity.ok(entityModel);
        } else {
            ResponseMessage errorResponse = new ResponseMessage("No se encontró ningún paciente con el ID especificado");
            EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityModel);
        }
    }
    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable Long id) {
        pacienteService.deletePaciente(id);
    } 
    class ResponseMessage {
        private String message;
    
        public ResponseMessage(String message) {
            this.message = message;
        }
    
        public String getMessage() {
            return message;
        }
    }
}

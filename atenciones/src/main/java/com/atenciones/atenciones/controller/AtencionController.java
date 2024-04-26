package com.atenciones.atenciones.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import java.util.stream.Collectors;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atenciones")
public class AtencionController {
    
    @Autowired
    private AtencionService atencionService;
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public CollectionModel<EntityModel<Atencion>> getAllAtencion(){
        List<Atencion> atenciones = atencionService.getAllAtencion();
        List<EntityModel<Atencion>> atencionResources = atenciones.stream()
            .map(atencion-> EntityModel.of(atencion,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionByid(atencion.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencion());
        CollectionModel<EntityModel<Atencion>> resources = CollectionModel.of(atencionResources, linkTo.withRel("atencion"));
        return resources;
    }   
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAtencionByid(@PathVariable Long id) {
        Optional<Atencion> atencionExist = atencionService.getAtencionByid(id);
        if (atencionExist.isPresent()) {
            Atencion atencion = atencionExist.get();
            EntityModel<Atencion> entityModel = EntityModel.of(atencion,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionByid(id)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencion()).withRel("all-atenciones"));
            return ResponseEntity.ok(entityModel);
        } else {
            ResponseMessage errorResponse = new ResponseMessage("No se encontró ningún atencion con el ID especificado");
            EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityModel);
        }
    }
    @PostMapping("/crear")
    public ResponseEntity<Object> createAtencion(@Valid @RequestBody Atencion atencion, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error de validación: ");
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
            ResponseMessage errorResponse = new ResponseMessage(errorMessage.toString());
            EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityModel);
        }
        String rutPaciente = atencion.getRut_paciente();
        Optional<Paciente> pacienteOptional = pacienteService.findByRut(rutPaciente);
    
        if (pacienteOptional.isEmpty()) {
            String errorMessage = "No se encontró ningún paciente con el rut: " + rutPaciente;
            ResponseMessage errorResponse = new ResponseMessage(errorMessage);
            EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityModel);
        } else {
            Atencion createdAtencion = atencionService.createAtencion(atencion);
            EntityModel<Atencion> entityModel = EntityModel.of(createdAtencion,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionByid(createdAtencion.getId())).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencion()).withRel("all-atenciones"));
            return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
        }
    }
@PutMapping("/{id}")
public ResponseEntity<Object> updateUsuario(@PathVariable Long id, @Valid @RequestBody Atencion atencion, BindingResult result) {
    Optional<Atencion> atencionExist = atencionService.getAtencionByid(id);
    if (atencionExist.isPresent()) {
        if (result.hasErrors()) {
            ResponseMessage errorResponse = new ResponseMessage("Error de validación: ");
            result.getAllErrors().forEach(error -> errorResponse.message += error.getDefaultMessage() + "; ");
            EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityModel);
        }
        String rutPaciente = atencion.getRut_paciente();
        Optional<Paciente> pacienteOptional = pacienteService.findByRut(rutPaciente);
        if (pacienteOptional.isPresent()) {
            Atencion updatedAtencion = atencionService.updateAtencion(id, atencion);
            ResponseMessage successResponse = new ResponseMessage("Atencion actualizada con éxito");
            EntityModel<Atencion> entityModel = EntityModel.of(updatedAtencion,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionByid(id)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencion()).withRel("all-atenciones"));
            return ResponseEntity.ok(entityModel);
        } else {
            ResponseMessage errorResponse = new ResponseMessage("No se encontró ningún paciente con el rut: " + rutPaciente);
            EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityModel);
        }
    } else {
        ResponseMessage errorResponse = new ResponseMessage("No se encontró ningún atencion con el ID especificado");
        EntityModel<ResponseMessage> entityModel = EntityModel.of(errorResponse);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityModel);
    }
}
    @DeleteMapping("/{id}")
    public void deleteAtencion(@PathVariable Long id) {
        atencionService.deleteAtencion(id);
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

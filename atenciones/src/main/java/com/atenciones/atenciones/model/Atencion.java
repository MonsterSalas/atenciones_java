package com.atenciones.atenciones.model;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Atencion")
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "especialidad")
    @NotBlank(message = "especialidad no puede ser nulo")
    private String especialidad;

    @Column(name = "medico")
    @NotBlank(message = "medico no puede ser nulo")
    private String medico;

    @Column(name = "rut_paciente")
    @NotBlank(message = "rut no puede ser nulo")
    private String rut_paciente;

    @Column(name = "fecha_atencion")
    @NotNull(message = "fecha_atencion no puede ser nulo")
    private Date fecha_atencion;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getRut_paciente() {
        return rut_paciente;
    }
    public void setRut_paciente(String rut_paciente) {
        this.rut_paciente = rut_paciente;
    }
    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public Date getFecha_atencion() {
        return fecha_atencion;
    }

    public void setFecha_atencion(Date fecha_atencion) {
        this.fecha_atencion = fecha_atencion;
    }

   
}

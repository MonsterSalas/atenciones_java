package com.atenciones.atenciones.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "rut")
    @NotBlank(message = "rut no puede ser nulo")
    private String rut;
    @Column(name = "nombre")
    @NotBlank(message = "nombre no puede ser nulo")
    private String nombre;

    public Long getId(){
        return  id;
    }
    public String getRut(){
        return  rut;
    }
    public String getNombre(){
        return nombre;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setRut(String rut) {
        this.rut = rut;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
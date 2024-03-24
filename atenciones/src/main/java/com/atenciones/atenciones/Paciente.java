package com.atenciones.atenciones;
import java.util.List;
public class Paciente {
    private int id;
    private String rut;
    private String nombre;
    private List<Atencion> atencion;
    // Constructor, getters y setters
    public Paciente(int id,String rut,String nombre,List<Atencion> atencion){
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.atencion = atencion;
    }
    public int getId(){
        return id;
    }
    public String getRut(){
        return rut;
    }
    public String getNombre(){
        return nombre;
    }
    public List<Atencion>getAtenciones(){
        return atencion;
    }
}
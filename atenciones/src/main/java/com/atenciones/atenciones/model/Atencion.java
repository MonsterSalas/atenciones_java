package com.atenciones.atenciones.model;
import java.util.Date;
public class Atencion {
    private int id;
    private String especialidad;
    private String medico;
    private Date fecha_atencion;
    // Constructor, getters y setters
    public Atencion(int id,String especialidad,String medico,Date fecha_atencion){
        this.id = id;
        this.especialidad = especialidad;
        this.medico = medico;
        this.fecha_atencion = fecha_atencion;
    }
    public int getId(){
        return id;
    }
    public String getEspecialidad(){
        return especialidad;
    }
    public String getMedico(){
        return medico;
    }
    public Date getFecha(){
        return fecha_atencion;
    }
}

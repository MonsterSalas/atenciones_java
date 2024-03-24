package com.atenciones.atenciones;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RestController
public class PacienteController {
    

    // Lista de pacientes predefinida
    private final List<Paciente> pacientes = new ArrayList<>();

    // Constructor para agregar pacientes predefinidos
    public PacienteController() {
        // Agregar pacientes a la lista
         // Agregar pacientes a la lista
        pacientes.add(new Paciente(1,"11111111-1", "Juan Pérez"
                    ,Arrays.asList(new Atencion(1, "Cardiología", "Dr. García", new Date()))));
        pacientes.add(new Paciente(2,"22222222-2", "María López"
                    ,Arrays.asList(new Atencion(2, "Dermatología", "Dr. Martínez", new Date()))));
        pacientes.add(new Paciente(3,"33333333-3", "Pedro Rodríguez", 
                    Arrays.asList(new Atencion(3, "Pediatría", "Dra. González", new Date()),
                    new Atencion(4, "Oftalmología", "Dr. Sánchez", new Date()))
        ));
    }

    // Endpoint para obtener todos los pacientes
    @GetMapping("/pacientes")
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    // Endpoint para obtener las atenciones de un paciente por su rut
    @GetMapping("/{id}/atenciones")
    public List<Atencion> obtenerAtencionesDePaciente(@PathVariable int id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId()==id ) {
                return paciente.getAtenciones();
            }
        }
        return null; // Retorna null si el paciente no se encuentra
    }
}

package com.atenciones.atenciones.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.atenciones.atenciones.model.Paciente;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PacienteRepositoryTest {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    public void guardarUsuarioTest() {
        Paciente paciente = new Paciente();
        paciente.setNombre("pruebas");
        paciente.setRut("123444");

        Paciente resultado = pacienteRepository.save(paciente);
        assertNotNull(resultado.getId());
        assertEquals("pruebas", resultado.getNombre());
    }
    
}

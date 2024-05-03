package com.atenciones.atenciones.controller;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.atenciones.atenciones.model.Paciente;
import com.atenciones.atenciones.service.AtencionService;
import com.atenciones.atenciones.service.PacienteService;
@WebMvcTest(PacienteController.class)
public class PacienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteServiceMock;

    @Mock
    private AtencionService atencionService;
        
    @InjectMocks
    private AtencionController atencionController;

    @Test
    public void obtenerTodosTest() throws Exception {

        Paciente paciente1 = new Paciente();
        
        paciente1.setNombre("pruebas");
        paciente1.setRut("1234");
        Paciente paciente2 = new Paciente();
        paciente2.setNombre("pruebas2");
        paciente2.setRut("0987");
        paciente2.setId(21L);
        List<Paciente> pacientes = Arrays.asList(paciente1, paciente2);
        when (pacienteServiceMock.getAllPaciente()).thenReturn (pacientes);
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.pacienteList", Matchers.hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.pacienteList[0].nombre", Matchers.is("pruebas")))
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.pacienteList[1].nombre", Matchers.is("pruebas2")));
        }

        @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testDeleteAtencion() {
        Long id = 1L;

        atencionController.deleteAtencion(id);

        verify(atencionService).deleteAtencion(id);
    }
}

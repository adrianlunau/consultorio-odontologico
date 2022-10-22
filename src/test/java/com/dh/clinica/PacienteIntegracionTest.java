package com.dh.clinica;

import com.dh.clinica.entity.Paciente;
import com.dh.clinica.repository.PacienteRespository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PacienteIntegracionTest {

    @Autowired
    private PacienteRespository pacienteRespository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deberiaCrearUnPacienteConExito() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNombre("NombreEJ");
        paciente.setApellido("ApellidoEJ");

        String response = this.mockMvc.perform(post("/pacientes")
                        .content(paciente.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(jsonPath("$.id", notNullValue()))
                        .andExpect(status().isCreated())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);


        int id = JsonPath.read(response, "$.id");
        Paciente pacienteGuardado = pacienteRespository.findById(Long.valueOf(id)).get();
        assertFalse(pacienteGuardado == null);
        assertEquals(pacienteGuardado.getNombre(), paciente.getNombre());
        assertEquals(pacienteGuardado.getApellido(), paciente.getApellido());
    }

    @Test
    public void deberiaActualizarUnPacienteConExito() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNombre("NombreInicial");
        paciente.setApellido("ApellidoInicial");
        Paciente pacienteAModificar = pacienteRespository.save(paciente);

        Paciente modificacion = new Paciente();
        modificacion.setId(pacienteAModificar.getId());
        modificacion.setNombre("Nombre modificado");
        modificacion.setApellido("Apellido modificado");

        String response = mockMvc.perform(put("/pacientes")
                .content(modificacion.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nombre", equalTo("Nombre modificado")))
                .andExpect(jsonPath("$.apellido", equalTo("Apellido modificado")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        int id = JsonPath.read(response, "$.id");
        Paciente pacienteModificado = pacienteRespository.findById(Long.valueOf(id)).get();
        assertEquals(pacienteModificado.getNombre(), "Nombre modificado");
        assertEquals(pacienteModificado.getApellido(), "Apellido modificado");
    }

    @Test
    public void deberiaBorrarUnPacienteExistente() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNombre("NombreInicial");
        paciente.setApellido("ApellidoInicial");
        Paciente pacienteAEliminar = pacienteRespository.save(paciente);

        mockMvc.perform(delete("/pacientes/{id}", String.valueOf(pacienteAEliminar.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isNoContent());

        Optional<Paciente> pacienteBorrado = pacienteRespository.findById(pacienteAEliminar.getId());
        assertTrue(pacienteBorrado.isEmpty());
    }

    @Test
    public void deberiaBuscarUnPacientePorSuId() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNombre("NombreInicial");
        paciente.setApellido("ApellidoInicial");
        Paciente pacienteABuscar = pacienteRespository.save(paciente);

        String response = mockMvc.perform(get("/pacientes/{id}", String.valueOf(pacienteABuscar.getId()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nombre", equalTo("NombreInicial")))
                .andExpect(jsonPath("$.apellido", equalTo("ApellidoInicial")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Optional<Paciente> pacienteEncontrado = pacienteRespository.findById(pacienteABuscar.getId());
        assertFalse(pacienteEncontrado.isEmpty());
    }

}

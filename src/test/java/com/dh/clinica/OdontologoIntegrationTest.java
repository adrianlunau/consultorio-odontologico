package com.dh.clinica;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.repository.OdontologoRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OdontologoIntegrationTest {

    @Autowired
    private OdontologoRepository odontologoRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deberiaCrearUnOdontologoConExito()  throws Exception {
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("PruebaNombre");
        odontologo.setApellido("PruebaApellido");
        odontologo.setMatricula(123456);

        String response = this.mockMvc.perform(post("/odontologos")
                .content(odontologo.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        int id = JsonPath.read(response, "$.id");
        Odontologo odontologoGuardado = odontologoRepository.findById(Long.valueOf(id)).get();
        assertFalse(odontologoGuardado == null);
        assertEquals(odontologoGuardado.getNombre(), odontologo.getNombre());
        assertEquals(odontologoGuardado.getApellido(), odontologo.getApellido());
        assertEquals(odontologoGuardado.getMatricula(), odontologo.getMatricula());

    }

    @Test
    public void deberiaBorrarUnOdontologoExistente() throws Exception {
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("PruebaNombre");
        odontologo.setApellido("PruebaApellido");
        odontologo.setMatricula(123456);
        Odontologo odontologoAEliminar = odontologoRepository.save(odontologo);

        mockMvc.perform(delete("/odontologos/{id}", String.valueOf(odontologoAEliminar.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isNoContent());


        Optional<Odontologo> odontologoBorrado = odontologoRepository.findById(odontologoAEliminar.getId());
        assertTrue(odontologoBorrado.isEmpty());
    }

    @Test
    public void deberiaListarTodosLosOdontologos() throws Exception {
        Odontologo odontologoUno = new Odontologo();
        odontologoUno.setNombre("Odontologo");
        odontologoUno.setApellido("Uno");

        Odontologo odontologoDos = new Odontologo();
        odontologoDos.setNombre("Odontologo");
        odontologoDos.setApellido("Dos");

        odontologoRepository.save(odontologoUno);
        odontologoRepository.save(odontologoDos);

        mockMvc.perform(get("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id", notNullValue()))
                .andExpect(status().isOk());

        List<Odontologo> odontologos = odontologoRepository.findAll();
        assertTrue(odontologos.size() > 0);
    }

    @Test
    public void deberiaModificarUnOdontologoConExito() throws Exception {
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Nombre inicial");
        odontologo.setApellido("Apellido inicial");
        odontologo.setMatricula(123456);
        Odontologo odontologoAModificar = odontologoRepository.save(odontologo);

        Odontologo modificacion = new Odontologo();
        modificacion.setId(odontologoAModificar.getId());
        modificacion.setNombre("Nuevo nombre");
        modificacion.setApellido("Nuevo apellido");
        modificacion.setMatricula(654321);

        String response = mockMvc.perform(put("/odontologos")
                .content(modificacion.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nombre", equalTo("Nuevo nombre")))
                .andExpect(jsonPath("$.apellido", equalTo("Nuevo apellido")))
                .andExpect(jsonPath("$.matricula", equalTo(654321)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        int id = JsonPath.read(response, "$.id");
        Odontologo odontologoModificado = odontologoRepository.findById(Long.valueOf(id)).get();
        assertEquals(odontologoModificado.getNombre(), "Nuevo nombre");
        assertEquals(odontologoModificado.getApellido(), "Nuevo apellido");
        assertEquals(odontologoModificado.getMatricula(), 654321);
    }
}

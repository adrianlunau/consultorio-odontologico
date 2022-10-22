package com.dh.clinica;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.repository.OdontologoRepository;
import com.dh.clinica.repository.PacienteRespository;
import com.dh.clinica.repository.TrunoRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TurnoIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TrunoRepository turnoRepository;
    @Autowired
    private PacienteRespository pacienteRespository;
    @Autowired
    private OdontologoRepository odontologoRepository;

    @Test
    public void deberiaCrearUnTurnoConUnPacienteYOdontologoExitentes() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNombre("Nombre");
        paciente.setApellido("Apellido");
        Paciente pacienteGuardado = pacienteRespository.save(paciente);

        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Odontologo");
        odontologo.setApellido("Apellido");
        odontologo.setMatricula(123456);
        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setDate(LocalDate.now());

        String response = this.mockMvc.perform(post("/turnos")
                .content(turno.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paciente", equalTo("Nombre Apellido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.odontologo", equalTo("Odontologo Apellido")))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        int id = JsonPath.read(response, "$.id");
        Turno turnoGuardado = turnoRepository.findById(Long.valueOf(id)).get();
        assertFalse(turnoGuardado == null);

    }
}

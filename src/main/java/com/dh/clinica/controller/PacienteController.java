package com.dh.clinica.controller;

import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping()
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) {
        Paciente pacienteCreado = pacienteService.guardar(paciente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pacienteCreado.getId())
                .toUri();

        return ResponseEntity.created(location).body(pacienteCreado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Long id) throws ResourceNotFoundException {
        Paciente paciente = pacienteService.buscar(id);
        return ResponseEntity.ok(paciente);
    }

    @GetMapping()
    public ResponseEntity<List<Paciente>> buscarTodos() {
        List<Paciente> pacientes = pacienteService.buscarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping()
    public ResponseEntity<Paciente> actualizar(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.actualizar(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

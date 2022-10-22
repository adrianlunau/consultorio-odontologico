package com.dh.clinica.controller;

import com.dh.clinica.dto.TurnoDto;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.PacienteService;
import com.dh.clinica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<TurnoDto> registrarTurno(@RequestBody Turno turno) throws ResourceNotFoundException {
        TurnoDto turnoCreado = turnoService.registrarTurno(turno);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(turnoCreado.getId())
                .toUri();

        return ResponseEntity.created(location).body(turnoCreado);
    }

    @GetMapping
    public ResponseEntity<List<TurnoDto>> listar() {
        return ResponseEntity.ok(turnoService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<TurnoDto> actualizarTurno(@RequestBody Turno turno) throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.actualizar(turno));
    }
}

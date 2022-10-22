package com.dh.clinica.service;


import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.mapper.TurnoMapper;
import com.dh.clinica.repository.PacienteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private PacienteRespository pacienteRespository;
    private TurnoMapper turnoMapper;

    @Autowired
    public PacienteService(PacienteRespository pacienteRespository, TurnoMapper turnoMapper) {
        this.pacienteRespository = pacienteRespository;
        this.turnoMapper = turnoMapper;
    }

    public Paciente guardar(Paciente p) {
        p.setFechaIngreso(new Date());
        return pacienteRespository.save(p);
    }

    public Paciente buscar(Long id) throws ResourceNotFoundException  {
        Optional<Paciente> pacienteOptional = pacienteRespository.findById(id);
        if (pacienteOptional.isEmpty()){
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        return pacienteOptional.get();
    }

    public List<Paciente> buscarTodos() {
        return pacienteRespository.findAll();
    }

    public void eliminar(Long id) throws ResourceNotFoundException {
        this.buscar(id);
        pacienteRespository.deleteById(id);
    }

    public Paciente actualizar(Paciente p) throws ResourceNotFoundException {
        this.buscar(p.getId());
        return pacienteRespository.save(p);
    }
}

package com.dh.clinica.service;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.OdontologoRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    Logger logger = LogManager.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo registrar(Odontologo odontologo) {
        logger.info("Se registro un odontologo con éxito.");
        return odontologoRepository.save(odontologo);
    }
    public void eliminar(Long id) throws ResourceNotFoundException {
        if(id == null || odontologoRepository.findById(id).isEmpty()) {
            logger.error("Se intentó eliminar un odontologo inexistente.");
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
        logger.info("Se elimino un odontologo con éxito.");
        odontologoRepository.deleteById(id);
    }
    public Odontologo buscar(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        if(odontologoOptional.isEmpty()) {
            logger.error("Se intentó buscar un odontologo inexistente.");
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
        logger.info("Devuelve un odontologo con éxito.");
        return odontologoRepository.findById(id).get();
    }
    public List<Odontologo> buscarTodos() {
        return odontologoRepository.findAll();
    }

    public Odontologo actualizar(Odontologo odontologo) throws ResourceNotFoundException {
        if(odontologo.getId() == null || odontologoRepository.findById(odontologo.getId()).isEmpty()) {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
        return odontologoRepository.save(odontologo);
    }
}

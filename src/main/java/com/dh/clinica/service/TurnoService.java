package com.dh.clinica.service;

import com.dh.clinica.dto.TurnoDto;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.exception.OperationNotPermittedException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.mapper.TurnoMapper;
import com.dh.clinica.repository.OdontologoRepository;
import com.dh.clinica.repository.PacienteRespository;
import com.dh.clinica.repository.TrunoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    private static final Logger logger = Logger.getLogger(TurnoService.class);
    @Autowired
    private TrunoRepository turnoRepository;
    @Autowired
    private PacienteRespository pacienteRespository;
    @Autowired
    private OdontologoRepository odontologoRepository;
    @Autowired
    private TurnoMapper turnoMapper;

    public TurnoDto registrarTurno(Turno turno) throws OperationNotPermittedException, ResourceNotFoundException {
        if (pacienteRespository.findById(turno.getPaciente().getId()).isEmpty() ||
                odontologoRepository.findById(turno.getOdontologo().getId()).isEmpty()) {
            throw new ResourceNotFoundException("Paciente u odontologo no encontrado.");
        }

        List<Turno> turnos = turnoRepository.findByOdontologo(turno.getOdontologo());

        if (turno.getDate().isBefore(LocalDate.now())) {
            logger.error("Se intentó crear un turno con fecha anterior a la actual.");
            throw new OperationNotPermittedException("No se puede registrar el turno debido a que la fecha ingresada" +
                    "no puede ser anterior a la fecha actual.");
        }

        for (Turno t : turnos) {
            if (t.getDate().isEqual(turno.getDate())) {
                logger.error("Se intentó crear un turno con odontologo no disponible para esa fecha.");
                throw new OperationNotPermittedException("No se puede registrar el turno debido a que el odontologo no " +
                        "esta disponible en esa fecha.");
            }
        }
        logger.info("Se creo un turno con exito");
        return  turnoMapper.entity2Dto(turnoRepository.save(turno));
    }
    public List<TurnoDto> listar(){
        List<Turno> turnos =turnoRepository.findAll();
        List<TurnoDto> turnosDto = new ArrayList<>();
        for (Turno turno: turnos) {
            TurnoDto dto = turnoMapper.entity2Dto(turno);
            turnosDto.add(dto);
        }
        return turnosDto;
    }
    public void eliminar(Long id) throws ResourceNotFoundException {
        this.buscar(id);
        logger.info("Se elimino un turno con éxito.");
        turnoRepository.deleteById(id);
    }
    public TurnoDto actualizar(Turno turno) throws ResourceNotFoundException {
        this.buscar(turno.getId());
        if (turno.getDate().isBefore(LocalDate.now())) {
            logger.error("Se intento modificar fecha de un turno a una fecha invalida.");
            throw new OperationNotPermittedException("No se puede registrar el turno debido a que la fecha ingresada" +
                    "no puede ser anterior a la fecha actual.");
        }
        logger.info("Se actualizo un turno con exito.");
        return turnoMapper.entity2Dto(turnoRepository.save(turno));
    }
    public TurnoDto buscar(Long id) throws ResourceNotFoundException {
        Optional<Turno> optionalTurno = turnoRepository.findById(id);
        if (optionalTurno.isEmpty()) {
            throw new ResourceNotFoundException("Turno no encontrado.");
        }
        TurnoDto result = turnoMapper.entity2Dto(optionalTurno.get());
        return result;
    }

}

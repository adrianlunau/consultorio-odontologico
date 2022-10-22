package com.dh.clinica.mapper;

import com.dh.clinica.dto.TurnoDto;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.repository.OdontologoRepository;
import com.dh.clinica.repository.PacienteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TurnoMapper {

    @Autowired
    private OdontologoRepository odontologoRepository;
    @Autowired
    private PacienteRespository pacienteRespository;

    public TurnoDto entity2Dto (Turno turno) {
        TurnoDto dto = new TurnoDto();
        dto.setId(turno.getId());
        dto.setOdontologo(odontologoRepository.findById(turno.getOdontologo().getId()).get().nombreCompleto());
        dto.setPaciente(pacienteRespository.findById(turno.getPaciente().getId()).get().nombreCompleto());
        dto.setFecha((turno.getDate()));

        return dto;
    }

    public Set<TurnoDto> entityList2DtoList (Set<Turno> turnos) {
        Set<TurnoDto> turnosDto = new HashSet<>();
        for (Turno turno: turnos) {
            TurnoDto turnoDto = entity2Dto(turno);
        }
        return turnosDto;
    }
}

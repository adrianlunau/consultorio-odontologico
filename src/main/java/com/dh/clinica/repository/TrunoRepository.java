package com.dh.clinica.repository;

import com.dh.clinica.dto.TurnoDto;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrunoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByDate(Date date);
    List<Turno> findByOdontologo(Odontologo odontologo);

}

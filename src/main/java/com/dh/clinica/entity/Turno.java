package com.dh.clinica.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontologo_id")
    private Odontologo odontologo;
    private LocalDate date;


    @Override
    public String toString() {
        return  "{\"id\": " + id +
                ", \"paciente\": " + paciente +
                ", \"odontologo\": " + odontologo +
                ", \"date\": \"" + date +
                "\"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Turno)) {
            return false;
        }
        Turno turno = (Turno) obj;
        if (turno.getOdontologo().equals(this.getOdontologo()) && turno.getDate().equals(this.getDate())) {
            return true;
        }
        return id.equals(turno.getId());
    }
}

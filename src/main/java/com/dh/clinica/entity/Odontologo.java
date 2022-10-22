package com.dh.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String apellido;
    private Integer matricula;

    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();

    public String nombreCompleto () {
        return this.nombre + " " + this.apellido;
    }


    @Override
    public String toString() {
        return "{\"id\":" + id +
                ", \"nombre\": \"" + nombre + "\"" +
                ", \"apellido\": \"" + apellido + "\"" +
                ", \"matricula\": \"" + matricula + "\"}";
    }
}

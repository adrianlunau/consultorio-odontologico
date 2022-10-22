package com.dh.clinica.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private Date fechaIngreso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
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
                ", \"dni\": \"" + dni + "\"}";
    }
}

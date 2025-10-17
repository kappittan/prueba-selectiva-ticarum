package com.ticarum.prueba_selectiva.infra.persistence;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.ticarum.prueba_selectiva.domain.model.Deporte;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "competiciones_deportivas")
public class CompeticionDeportivaEntity {

    @Id
    @Column(name = "competicion_deportiva_id", nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "deporte", nullable = false)
    private Deporte deporte;

    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

    @Column(name = "num_pistas_jornada", nullable = false)
    private int numPistasJornada;

    // Esta columna puede ser null, ya que una competición se puede crear y
    // posteriormente calcular emparejamientos para la primera jornada.
    @ElementCollection
    @CollectionTable(name = "competicion_deportiva_jornadas", joinColumns = @JoinColumn(name = "competicion_deportiva_id"))
    @Column(name = "jornadas", nullable = true)
    private List<UUID> jornadas;

    // Esta columna también puede ser null, ya que al momento de crear una
    // competición deportiva no habrá equipos asignados a ella.
    @ElementCollection
    @CollectionTable(name = "competicion_deportiva_equipos", joinColumns = @JoinColumn(name = "competicion_deportiva_id"))
    @Column(name = "equipos", nullable = true)
    private List<UUID> equipos;

    protected CompeticionDeportivaEntity() {

    }

    public CompeticionDeportivaEntity(UUID id, String nombre, Deporte deporte, Date fechaInicio, Date fechaFin,
            int numPistasJornada, List<UUID> jornadas, List<UUID> equipos) {
        this.id = id;
        this.nombre = nombre;
        this.deporte = deporte;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numPistasJornada = numPistasJornada;
        this.jornadas = jornadas;
        this.equipos = equipos;
    }

    public UUID getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Deporte getDeporte() {
        return this.deporte;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public int getNumPistasJornada() {
        return this.numPistasJornada;
    }

    public List<UUID> getJornadas() {
        return this.jornadas;
    }

    public List<UUID> getEquipos() {
        return this.equipos;
    }
}

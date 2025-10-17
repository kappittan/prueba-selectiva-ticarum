package com.ticarum.prueba_selectiva.domain.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CompeticionDeportiva extends Entity {

    private String nombre;
    private Deporte deporte;
    private Optional<List<UUID>> jornadas;// Una competición deportiva puede crearse sin jornadas, y posteriormente
                                          // añadirlas
    private Optional<List<UUID>> equipos; // Al momento de crear una competición deportiva, no existen equipos
    private Date fechaInicio;
    private Date fechaFin;
    private int numPistasJornada;

    public CompeticionDeportiva(UUID id, String nombre, Deporte deporte, Optional<List<UUID>> jornadas,
            Optional<List<UUID>> equipos,
            Date fechaInicio, Date fechaFin, int numPistasJornada) {
        super(id);
        this.nombre = nombre;
        this.deporte = deporte;
        this.jornadas = jornadas;
        this.equipos = equipos;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numPistasJornada = numPistasJornada;
    }

    public CompeticionDeportiva(String nombre, Deporte deporte,
            Date fechaInicio, Date fechaFin, int numPistasJornada) {
        this(null, nombre, deporte, Optional.empty(), Optional.empty(), fechaInicio, fechaFin, numPistasJornada);
    }

    public String getNombre() {
        return nombre;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public Optional<List<UUID>> getJornadas() {
        return jornadas;
    }

    public void añadirJornada(UUID jornadaId) {

        if (this.jornadas.isPresent()) {
            this.jornadas.get().add(jornadaId);
        } else {
            List<UUID> nuevasJornadas = new LinkedList<>();
            nuevasJornadas.add(jornadaId);
            this.jornadas = Optional.of(nuevasJornadas);
        }
    }

    public void añadirPrimeraJornada(UUID jornadaId) {
        this.añadirJornada(jornadaId);
    }

    public Optional<List<UUID>> getEquipos() {
        return equipos;
    }

    public void registrarEquipo(UUID equipoId) {

        if (this.equipos.isPresent()) {
            this.equipos.get().add(equipoId);
        } else {
            List<UUID> nuevosEquipos = new LinkedList<>();
            nuevosEquipos.add(equipoId);
            this.equipos = Optional.of(nuevosEquipos);
        }
    }

    public boolean equipoRegistrado(UUID equipoId) {
        if (this.equipos.isPresent()) {
            return this.equipos.get().contains(equipoId);
        } else {
            return false;
        }
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public int getNumPistasJornada() {
        return numPistasJornada;
    }
}

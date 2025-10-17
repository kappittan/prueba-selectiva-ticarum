package com.ticarum.prueba_selectiva.dto;

import java.util.Date;

import com.ticarum.prueba_selectiva.domain.model.Deporte;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CompeticionDeportivaCreateDTO {

    @NotBlank(message = "El nombre de la competición no puede estar vacío")
    private String nombre;

    @NotNull(message = "El deporte de la competición no puede estar vacío")
    private Deporte deporte;

    @NotNull(message = "La fecha de inicio de la competición no puede estar vacía")
    @FutureOrPresent(message = "La fecha de inicio de la competición no puede ser anterior a la fecha actual")
    private Date fechaInicio;

    @NotNull(message = "La fecha de fin de la competición no puede estar vacía")
    @FutureOrPresent(message = "La fecha de fin de la competición no puede ser anterior a la fecha actual")
    private Date fechaFin;

    @NotNull(message = "El número de pistas por jornada no puede estar vacío")
    @Min(value = 1, message = "El número de pistas por jornada debe ser al menos 1")
    @Max(value = 100, message = "El número de pistas por jornada debe ser como máximo 100")
    private int numPistasJornada;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getNumPistasJornada() {
        return numPistasJornada;
    }

    public void setNumPistasJornada(int numPistasJornada) {
        this.numPistasJornada = numPistasJornada;
    }
}

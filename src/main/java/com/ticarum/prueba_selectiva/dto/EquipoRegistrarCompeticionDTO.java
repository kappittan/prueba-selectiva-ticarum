package com.ticarum.prueba_selectiva.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class EquipoRegistrarCompeticionDTO {

    @NotNull(message = "El id del equipo no puede estar vacío")
    private UUID equipo;

    public UUID getEquipo() {
        return this.equipo;
    }

}

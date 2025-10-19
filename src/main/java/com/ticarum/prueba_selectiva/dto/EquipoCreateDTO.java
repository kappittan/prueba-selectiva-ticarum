package com.ticarum.prueba_selectiva.dto;

import jakarta.validation.constraints.NotBlank;

public class EquipoCreateDTO {

    @NotBlank(message = "El nombre de la competición no puede estar vacío")
    private String nombre;

    public EquipoCreateDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

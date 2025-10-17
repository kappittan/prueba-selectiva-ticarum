package com.ticarum.prueba_selectiva.exceptions.equipos;

import org.springframework.http.HttpStatus;

import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;

public class ExcepcionNombreEquipoNoDisponible extends ExcepcionBase {

    public ExcepcionNombreEquipoNoDisponible() {
        super("El nombre del equipo ya está en uso", HttpStatus.BAD_REQUEST.value());
    }

}

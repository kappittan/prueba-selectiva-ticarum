package com.ticarum.prueba_selectiva.exceptions.equipos;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;

public class ExcepcionEquipoNoEncontrado extends ExcepcionBase {

    public ExcepcionEquipoNoEncontrado(UUID id) {
        super(String.format("El equipo con id %s no existe", id), HttpStatus.NOT_FOUND.value());
    }

}

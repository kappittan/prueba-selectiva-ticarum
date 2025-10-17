package com.ticarum.prueba_selectiva.exceptions.partidos;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;

public class ExcepcionPartidoNoEncontrado extends ExcepcionBase {

    public ExcepcionPartidoNoEncontrado(UUID id) {
        super(String.format("El partido con id %s no existe", id), HttpStatus.NOT_FOUND.value());
    }
}

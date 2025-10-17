package com.ticarum.prueba_selectiva.exceptions.jornadas;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;

public class ExcepcionJornadaNoEncontrada extends ExcepcionBase {

    public ExcepcionJornadaNoEncontrada(UUID id) {
        super(String.format("La jornada con id %s no existe", id), HttpStatus.NOT_FOUND.value());
    }

}

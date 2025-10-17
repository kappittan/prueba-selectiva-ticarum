package com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;

public class ExcepcionCompeticionNoEncontrada extends ExcepcionBase {

    public ExcepcionCompeticionNoEncontrada(UUID id) {
        super(String.format("La competicion con id %s no existe", id), HttpStatus.NOT_FOUND.value());
    }

}

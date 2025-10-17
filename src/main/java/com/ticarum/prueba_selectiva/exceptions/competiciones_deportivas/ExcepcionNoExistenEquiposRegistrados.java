package com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;

public class ExcepcionNoExistenEquiposRegistrados extends ExcepcionBase {

    public ExcepcionNoExistenEquiposRegistrados(UUID id) {
        super(String.format(
                "No es posible generar la primera jornada para la competición deportiva con id %s, ya que no existen equipos registrados en ella.",
                id), HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

}

package com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;

public class ExcepcionPrimeraJornadaNoGenerada extends ExcepcionBase {

    public ExcepcionPrimeraJornadaNoGenerada(UUID id) {
        super(String.format("La primera jornada no ha sido generada para la competicion deportiva con id %s.", id),
                HttpStatus.NOT_FOUND.value());
    }

}

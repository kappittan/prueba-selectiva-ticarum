package com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas;

import org.springframework.http.HttpStatus;
import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;

public class ExcepcionPrimeraJornadaYaGenerada extends ExcepcionBase {

    public ExcepcionPrimeraJornadaYaGenerada() {
        super("La primera jornada ya ha sido generada para esta competición deportiva.", HttpStatus.CONFLICT.value());
    }

}

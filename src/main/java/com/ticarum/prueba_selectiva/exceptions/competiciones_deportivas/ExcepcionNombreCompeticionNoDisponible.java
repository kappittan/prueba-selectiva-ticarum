package com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas;

import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;
import org.springframework.http.HttpStatus;

public class ExcepcionNombreCompeticionNoDisponible extends ExcepcionBase {

    public ExcepcionNombreCompeticionNoDisponible() {
        super("El nombre de la competición ya está siendo usado", HttpStatus.BAD_REQUEST.value());
    }
}

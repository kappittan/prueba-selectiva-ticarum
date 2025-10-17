package com.ticarum.prueba_selectiva.exceptions.equipos;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import com.ticarum.prueba_selectiva.exceptions.ExcepcionBase;

public class ExcepcionEquipoYaRegistradoEnCompeticion extends ExcepcionBase {

    public ExcepcionEquipoYaRegistradoEnCompeticion(UUID equipo, UUID competicion) {
        super(String.format("El equipo con id %s ya se encuentra registrado en la competicion con id %s", equipo,
                competicion), HttpStatus.CONFLICT.value());
    }

}

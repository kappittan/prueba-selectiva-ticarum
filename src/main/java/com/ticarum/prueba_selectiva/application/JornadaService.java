package com.ticarum.prueba_selectiva.application;

import java.util.UUID;

import com.ticarum.prueba_selectiva.domain.model.Jornada;
import com.ticarum.prueba_selectiva.dto.JornadaCreateDTO;

public interface JornadaService {

    Jornada añadirJornada(JornadaCreateDTO jornadaValues);

    Jornada obtenerJornada(UUID jornadaId);
}

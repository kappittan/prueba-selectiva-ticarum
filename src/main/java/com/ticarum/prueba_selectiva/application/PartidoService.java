package com.ticarum.prueba_selectiva.application;

import java.util.UUID;

import com.ticarum.prueba_selectiva.domain.model.Partido;
import com.ticarum.prueba_selectiva.dto.PartidoCreateDTO;

public interface PartidoService {

    Partido añadirPartido(PartidoCreateDTO partidoValues);

    Partido obtenerPartido(UUID partidoId);
}

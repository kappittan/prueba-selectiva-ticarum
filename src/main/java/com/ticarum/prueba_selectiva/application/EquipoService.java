package com.ticarum.prueba_selectiva.application;

import java.util.UUID;

import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.dto.EquipoCreateDTO;

public interface EquipoService {

    Equipo añadirEquipo(UUID competicionId, EquipoCreateDTO equipoValues);

    Equipo getEquipo(UUID equipoId);
}

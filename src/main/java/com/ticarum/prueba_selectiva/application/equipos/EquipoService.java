package com.ticarum.prueba_selectiva.application.equipos;

import java.util.UUID;

import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.dto.EquipoCreateDTO;

public interface EquipoService {

    Equipo crearEquipo(EquipoCreateDTO equipoValues);

    Equipo añadirEquipoACompeticion(UUID equipoId, UUID competicionId);

    Equipo getEquipo(UUID equipoId);
}

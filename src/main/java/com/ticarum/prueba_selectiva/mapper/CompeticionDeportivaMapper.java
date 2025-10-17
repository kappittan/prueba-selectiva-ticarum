package com.ticarum.prueba_selectiva.mapper;

import java.util.List;
import java.util.Optional;

import com.ticarum.prueba_selectiva.domain.model.CompeticionDeportiva;
import com.ticarum.prueba_selectiva.infra.persistence.CompeticionDeportivaEntity;

public class CompeticionDeportivaMapper {

    public static CompeticionDeportiva toDomain(CompeticionDeportivaEntity entidad) {
        return new CompeticionDeportiva(entidad.getId(), entidad.getNombre(), entidad.getDeporte(),
                Optional.ofNullable(entidad.getJornadas()), Optional.ofNullable(entidad.getEquipos()),
                entidad.getFechaInicio(), entidad.getFechaFin(),
                entidad.getNumPistasJornada());
    }

    public static CompeticionDeportivaEntity toPersistence(CompeticionDeportiva competicion) {
        return new CompeticionDeportivaEntity(competicion.getId(), competicion.getNombre(), competicion.getDeporte(),
                competicion.getFechaInicio(), competicion.getFechaFin(), competicion.getNumPistasJornada(),
                competicion.getJornadas().isPresent() ? competicion.getJornadas().get() : List.of(),
                competicion.getEquipos().isPresent() ? competicion.getEquipos().get() : List.of());
    }
}

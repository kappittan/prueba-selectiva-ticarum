package com.ticarum.prueba_selectiva.mapper;

import java.util.List;
import java.util.Optional;

import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.infra.persistence.EquipoEntity;

public class EquipoMapper {

    public static Equipo toDomain(EquipoEntity entidad) {
        return new Equipo(entidad.getId(), entidad.getNombre(), Optional.ofNullable(entidad.getCompeticionesActivas()));
    }

    public static EquipoEntity toEntity(Equipo equipo) {
        return new EquipoEntity(equipo.getId(), equipo.getNombre(),
                equipo.getCompeticionesActivas().isPresent() ? equipo.getCompeticionesActivas().get() : List.of());
    }
}

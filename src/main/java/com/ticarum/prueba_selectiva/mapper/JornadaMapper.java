package com.ticarum.prueba_selectiva.mapper;

import java.util.List;
import java.util.Optional;

import com.ticarum.prueba_selectiva.domain.model.Jornada;
import com.ticarum.prueba_selectiva.infra.persistence.JornadaEntity;

public class JornadaMapper {

    public static Jornada toDomain(JornadaEntity entidad) {
        return new Jornada(entidad.getId(), entidad.getCompeticion(), entidad.getPartidos(),
                Optional.ofNullable(entidad.getEquiposNoAsignados()));
    }

    public static JornadaEntity toPersistence(Jornada jornada) {
        return new JornadaEntity(jornada.getId(), jornada.getCompeticion(), jornada.getPartidos(),
                jornada.getEquiposNoAsignados().isPresent() ? jornada.getEquiposNoAsignados().get() : List.of());
    }
}

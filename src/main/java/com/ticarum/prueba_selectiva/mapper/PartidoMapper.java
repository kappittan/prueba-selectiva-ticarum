package com.ticarum.prueba_selectiva.mapper;

import com.ticarum.prueba_selectiva.domain.model.Partido;
import com.ticarum.prueba_selectiva.infra.persistence.PartidoEntity;

public class PartidoMapper {

    public static Partido toDomain(PartidoEntity entidad) {
        return new Partido(entidad.getId(), entidad.getEquipo1(), entidad.getEquipo2(), entidad.getFecha());
    }

    public static PartidoEntity toPersistence(Partido partido) {
        return new PartidoEntity(partido.getId(), partido.getEquipo1(), partido.getEquipo2(), partido.getFecha());
    }
}

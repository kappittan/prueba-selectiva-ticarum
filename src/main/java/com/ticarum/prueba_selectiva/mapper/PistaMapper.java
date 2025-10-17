package com.ticarum.prueba_selectiva.mapper;

import com.ticarum.prueba_selectiva.domain.model.Pista;
import com.ticarum.prueba_selectiva.infra.persistence.PistaEntity;

public class PistaMapper {

    public static Pista toDomain(PistaEntity entidad) {
        return new Pista(entidad.getId(), entidad.getNombre());
    }

    public static PistaEntity toPersistence(Pista pista) {
        return new PistaEntity(pista.getId(), pista.getNombre());
    }
}

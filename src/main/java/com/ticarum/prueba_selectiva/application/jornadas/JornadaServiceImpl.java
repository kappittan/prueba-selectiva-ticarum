package com.ticarum.prueba_selectiva.application.jornadas;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticarum.prueba_selectiva.domain.model.Jornada;
import com.ticarum.prueba_selectiva.dto.JornadaCreateDTO;
import com.ticarum.prueba_selectiva.infra.JornadaRepository;
import com.ticarum.prueba_selectiva.infra.persistence.JornadaEntity;
import com.ticarum.prueba_selectiva.mapper.JornadaMapper;
import com.ticarum.prueba_selectiva.exceptions.jornadas.ExcepcionJornadaNoEncontrada;

@Service
public class JornadaServiceImpl implements JornadaService {

    @Autowired
    private JornadaRepository jornadaRepository;

    @Override
    public Jornada añadirJornada(JornadaCreateDTO jornadaValues) {
        // 1. Creamos la jornada en el dominio
        Jornada nuevaJornada = new Jornada(jornadaValues.getCompeticion(), jornadaValues.getPartidos(),
                jornadaValues.getEquiposNoAsignados());

        // 2. Guardamos la jornada en la base de datos
        JornadaEntity entidad = jornadaRepository.save(JornadaMapper.toPersistence(nuevaJornada));

        // 3. Devolvemos la jornada convertida a dominio
        return JornadaMapper.toDomain(entidad);

    }

    @Override
    public Jornada obtenerJornada(UUID jornadaId) {
        // 1. Comprobar si la jornada existe
        if (!this.jornadaRepository.findById(jornadaId).isPresent()) {
            throw new ExcepcionJornadaNoEncontrada(jornadaId);
        }

        // 2. Obtener la jornada de la base de datos
        JornadaEntity entidad = this.jornadaRepository.findById(jornadaId).get();

        // 3. Devolver la jornada convertida a dominio
        return JornadaMapper.toDomain(entidad);
    }

}

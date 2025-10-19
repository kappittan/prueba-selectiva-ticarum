package com.ticarum.prueba_selectiva.application.partidos;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticarum.prueba_selectiva.domain.model.Partido;
import com.ticarum.prueba_selectiva.dto.PartidoCreateDTO;
import com.ticarum.prueba_selectiva.exceptions.partidos.ExcepcionPartidoNoEncontrado;
import com.ticarum.prueba_selectiva.infra.PartidoRepository;
import com.ticarum.prueba_selectiva.infra.persistence.PartidoEntity;
import com.ticarum.prueba_selectiva.mapper.PartidoMapper;

@Service
public class PartidoServiceImpl implements PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    @Override
    public Partido añadirPartido(PartidoCreateDTO partidoValues) {
        // 1. Creamos el partido en el dominio
        Partido nuevoPartido = new Partido(partidoValues.getEquipo1(), partidoValues.getEquipo2(),
                partidoValues.getFecha());

        // 2. Guardamos el partido en la base de datos
        PartidoEntity entidad = partidoRepository.save(PartidoMapper.toPersistence(nuevoPartido));

        // 3. Devolvemos el partido guardado convertido a dominio
        return PartidoMapper.toDomain(entidad);
    }

    @Override
    public Partido obtenerPartido(UUID partidoId) {
        // 1. Validamos que el partido exista
        if (!this.partidoRepository.findById(partidoId).isPresent()) {
            throw new ExcepcionPartidoNoEncontrado(partidoId);
        }

        // 2. Obtenemos el partido de la base de datos
        PartidoEntity entidad = this.partidoRepository.findById(partidoId).get();

        // 3. Devolvemos el partido convertido a dominio
        return PartidoMapper.toDomain(entidad);
    }

}

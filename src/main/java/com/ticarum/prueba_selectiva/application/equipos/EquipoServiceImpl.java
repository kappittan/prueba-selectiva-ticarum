package com.ticarum.prueba_selectiva.application.equipos;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.dto.EquipoCreateDTO;
import com.ticarum.prueba_selectiva.exceptions.equipos.ExcepcionEquipoNoEncontrado;
import com.ticarum.prueba_selectiva.exceptions.equipos.ExcepcionNombreEquipoNoDisponible;
import com.ticarum.prueba_selectiva.infra.EquipoRepository;
import com.ticarum.prueba_selectiva.infra.persistence.EquipoEntity;
import com.ticarum.prueba_selectiva.mapper.EquipoMapper;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public Equipo crearEquipo(EquipoCreateDTO equipoValues) {
        // 1. Comprobamos que el nombre del equipo no esté ya en uso, es decir, que no
        // se haya registrado ya en la competición
        if (equipoRepository.findByNombre(equipoValues.getNombre()).size() > 0) {
            throw new ExcepcionNombreEquipoNoDisponible();
        }

        // 2. Creamos el nuevo equipo
        Equipo nuevoEquipo = new Equipo(equipoValues.getNombre());

        // 3. Guardamos el equipo en la base de datos
        EquipoEntity entidad = equipoRepository.save(EquipoMapper.toEntity(nuevoEquipo));

        // 4. Devolvemos el equipo guardado convertido a dominio
        return EquipoMapper.toDomain(entidad);
    }

    @Override
    public Equipo getEquipo(UUID equipoId) {
        // 1. Validamos que el equipo exista
        if (!this.equipoRepository.findById(equipoId).isPresent()) {
            throw new ExcepcionEquipoNoEncontrado(equipoId);
        }

        // 2. Obtenemos el equipo de la base de datos y lo convertimos a dominio
        return EquipoMapper.toDomain(equipoRepository.findById(equipoId).get());
    }

    @Override
    public Equipo añadirEquipoACompeticion(UUID equipoId, UUID competicionId) {
        // 1. Validamos que el equipo exista
        if (!this.equipoRepository.findById(equipoId).isPresent()) {
            throw new ExcepcionEquipoNoEncontrado(equipoId);
        }

        // 2. Obtenemos el equipo de la base de datos y lo convertimos a dominio
        Equipo equipo = EquipoMapper.toDomain(equipoRepository.findById(equipoId).get());

        // 3. Añadimos la competición al equipo
        equipo.añadirCompeticionActiva(competicionId);

        // 4. Guardamos el equipo actualizado en la base de datos
        EquipoEntity entidadActualizada = equipoRepository.save(EquipoMapper.toEntity(equipo));

        // 5. Devolvemos el equipo actualizado convertido a dominio
        return EquipoMapper.toDomain(entidadActualizada);
    }

}

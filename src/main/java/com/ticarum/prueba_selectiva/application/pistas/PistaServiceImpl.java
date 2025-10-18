package com.ticarum.prueba_selectiva.application.pistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticarum.prueba_selectiva.domain.model.Pista;
import com.ticarum.prueba_selectiva.infra.PistaRepository;
import com.ticarum.prueba_selectiva.mapper.PistaMapper;
import com.ticarum.prueba_selectiva.infra.persistence.PistaEntity;

@Service
public class PistaServiceImpl implements PistaService {

    @Autowired
    private PistaRepository pistaRepository;

    @Override
    public Pista añadirPista(Pista nuevaPista) {

        // Convertimos la clase del dominio (Pista) en una clase persistente
        // (PistaEntity), y guardamos en el repositorio
        PistaEntity entidad = pistaRepository.save(PistaMapper.toPersistence(nuevaPista));

        // Devolvemos lo que nos devuelve el repositorio, tras mapear a dominio
        return PistaMapper.toDomain(entidad);
    }

}

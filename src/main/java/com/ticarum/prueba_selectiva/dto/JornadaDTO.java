package com.ticarum.prueba_selectiva.dto;

import java.util.List;

import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.domain.model.Partido;

public class JornadaDTO {

    List<Partido> partidos;

    List<Equipo> equiposNoAsignados;

    public JornadaDTO(List<Partido> partidos, List<Equipo> equiposNoAsignados) {
        this.partidos = partidos;
        this.equiposNoAsignados = equiposNoAsignados;
    }
}

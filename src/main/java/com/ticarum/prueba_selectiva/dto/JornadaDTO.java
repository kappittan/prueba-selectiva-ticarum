package com.ticarum.prueba_selectiva.dto;

import java.util.List;

import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.domain.model.Partido;

public class JornadaDTO {

    private List<Partido> partidos;

    private List<Equipo> equiposNoAsignados;

    public JornadaDTO(List<Partido> partidos, List<Equipo> equiposNoAsignados) {
        this.partidos = partidos;
        this.equiposNoAsignados = equiposNoAsignados;
    }

    public List<Partido> getPartidos() {
        return this.partidos;
    }

    public List<Equipo> getEquiposNoAsignados() {
        return this.equiposNoAsignados;
    }
}

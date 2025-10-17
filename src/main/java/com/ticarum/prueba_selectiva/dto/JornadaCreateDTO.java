package com.ticarum.prueba_selectiva.dto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JornadaCreateDTO {

    private UUID competicion;

    private List<UUID> partidos;

    private Optional<List<UUID>> equiposNoAsignados;

    JornadaCreateDTO() {
    }

    public JornadaCreateDTO(UUID competicion, List<UUID> partidos,
            Optional<List<UUID>> equiposNoAsignados) {
        this.competicion = competicion;
        this.partidos = partidos;
        this.equiposNoAsignados = equiposNoAsignados;
    }

    public UUID getCompeticion() {
        return this.competicion;
    }

    public void setCompeticion(UUID competicion) {
        this.competicion = competicion;
    }

    public List<UUID> getPartidos() {
        return this.partidos;
    }

    public void setPartidos(List<UUID> partidos) {
        this.partidos = partidos;
    }

    public Optional<List<UUID>> getEquiposNoAsignados() {
        return this.equiposNoAsignados;
    }

    public void setEquiposNoAsignados(Optional<List<UUID>> equiposNoAsignados) {
        this.equiposNoAsignados = equiposNoAsignados;
    }
}

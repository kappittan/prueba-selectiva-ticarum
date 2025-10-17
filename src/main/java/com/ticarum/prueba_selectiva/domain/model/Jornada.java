package com.ticarum.prueba_selectiva.domain.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Jornada extends Entity {

    private UUID competicion;

    private List<UUID> partidos;

    // Pueden existir o no equipos no asignados tras los emparejamientos de la
    // primera jornada.
    private Optional<List<UUID>> equiposNoAsignados;

    public Jornada(UUID id, UUID competicion, List<UUID> partidos,
            Optional<List<UUID>> equiposNoAsignados) {
        super(id);
        this.competicion = competicion;
        this.partidos = partidos;
        this.equiposNoAsignados = equiposNoAsignados;
    }

    public Jornada(UUID competicion, List<UUID> partidos,
            Optional<List<UUID>> equiposNoAsignados) {
        this(null, competicion, partidos, equiposNoAsignados);
    }

    public List<UUID> getPartidos() {
        return this.partidos;
    }

    public Optional<List<UUID>> getEquiposNoAsignados() {
        return this.equiposNoAsignados;
    }

    public UUID getCompeticion() {
        return this.competicion;
    }
}

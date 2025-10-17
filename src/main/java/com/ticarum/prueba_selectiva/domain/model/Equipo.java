package com.ticarum.prueba_selectiva.domain.model;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Equipo extends Entity {

    private String nombre;
    private List<UUID> competicionesActivas;

    public Equipo(UUID id, String nombre, List<UUID> competicionesActivas) {
        super(id);
        this.nombre = nombre;
        this.competicionesActivas = competicionesActivas;
    }

    // Este constructor se utiliza la primera vez que se crea un equipo, ya que
    // tendrá solo una competición asociada.
    public Equipo(String nombre, UUID competicionActiva) {
        this(null, nombre, new LinkedList<UUID>(List.of(competicionActiva)));
    }

    public String getNombre() {
        return this.nombre;
    }

    public List<UUID> getCompeticionesActivas() {
        return this.competicionesActivas;
    }
}

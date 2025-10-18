package com.ticarum.prueba_selectiva.domain.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Equipo extends Entity {

    private String nombre;
    private Optional<List<UUID>> competicionesActivas;

    public Equipo(UUID id, String nombre, Optional<List<UUID>> competicionesActivas) {
        super(id);
        this.nombre = nombre;
        this.competicionesActivas = competicionesActivas;
    }

    // Este constructor se utiliza la primera vez que se crea un equipo
    public Equipo(String nombre) {
        this(null, nombre, Optional.empty());
    }

    public String getNombre() {
        return this.nombre;
    }

    public Optional<List<UUID>> getCompeticionesActivas() {
        return this.competicionesActivas;
    }

    public void añadirCompeticionActiva(UUID competicionId) {
        if (this.competicionesActivas.isPresent()) {
            this.competicionesActivas.get().add(competicionId);
        } else {
            List<UUID> competiciones = new LinkedList<>();
            competiciones.add(competicionId);
            this.competicionesActivas = Optional.of(competiciones);
        }
    }
}

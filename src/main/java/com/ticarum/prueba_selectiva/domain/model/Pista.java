package com.ticarum.prueba_selectiva.domain.model;

import java.util.UUID;

public class Pista extends Entity {

    private String nombre;

    public Pista(String nombre) {
        super();
        this.nombre = nombre;
    }

    public Pista(UUID id, String nombre) {
        super(id);
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}

package com.ticarum.prueba_selectiva.infra.persistence;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pistas")
public class PistaEntity {

    @Id
    @Column(name = "pista_id", nullable = false)
    private UUID id;

    @Column(name = "id", nullable = false)
    private String nombre;

    protected PistaEntity() {
    }

    public PistaEntity(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public UUID getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

}

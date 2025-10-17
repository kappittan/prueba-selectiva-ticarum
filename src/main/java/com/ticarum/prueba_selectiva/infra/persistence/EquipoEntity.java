package com.ticarum.prueba_selectiva.infra.persistence;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipos")
public class EquipoEntity {

    @Id
    @Column(name = "equipo_id", nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Las competiciones activas pueden tener un valor null, ya que un equipo puede
    // crearse y posteriormente registrarse en una competición deportiva
    @ElementCollection
    @CollectionTable(name = "equipo_competiciones_activas", joinColumns = @JoinColumn(name = "equipo_id"))
    @Column(name = "competiciones_activas", nullable = true)
    private List<UUID> competicionesActivas;

    protected EquipoEntity() {

    }

    public EquipoEntity(UUID id, String nombre, List<UUID> competicionesActivas) {
        this.id = id;
        this.nombre = nombre;
        this.competicionesActivas = competicionesActivas;
    }

    public UUID getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public List<UUID> getCompeticionesActivas() {
        return this.competicionesActivas;
    }
}

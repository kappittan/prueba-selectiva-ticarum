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
@Table(name = "jornadas")
public class JornadaEntity {

    @Id
    @Column(name = "jornada_id")
    private UUID id;

    @Column(name = "competicion", nullable = false)
    private UUID competicion;

    @ElementCollection
    @CollectionTable(name = "jornada_partidos", joinColumns = @JoinColumn(name = "jornada_id"))
    @Column(name = "partidos", nullable = false)
    private List<UUID> partidos;

    @ElementCollection
    @CollectionTable(name = "jornada_equipos_no_asignados", joinColumns = @JoinColumn(name = "jornada_id"))
    @Column(name = "equipos_no_asignados", nullable = true)
    private List<UUID> equiposNoAsignados;

    protected JornadaEntity() {

    }

    public JornadaEntity(UUID id, UUID competicion, List<UUID> partidos, List<UUID> equiposNoAsignados) {
        this.id = id;
        this.competicion = competicion;
        this.partidos = partidos;
        this.equiposNoAsignados = equiposNoAsignados;
    }

    public UUID getId() {
        return this.id;
    }

    public List<UUID> getPartidos() {
        return this.partidos;
    }

    public List<UUID> getEquiposNoAsignados() {
        return this.equiposNoAsignados;
    }

    public UUID getCompeticion() {
        return this.competicion;
    }
}

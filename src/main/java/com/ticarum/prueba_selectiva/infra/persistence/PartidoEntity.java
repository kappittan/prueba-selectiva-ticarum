package com.ticarum.prueba_selectiva.infra.persistence;

import java.util.Date;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "partidos")
public class PartidoEntity {

    @Id
    @Column(name = "partido_id", nullable = false)
    private UUID id;

    @Column(name = "equipo1", nullable = false)
    private UUID equipo1;

    @Column(name = "equipo2", nullable = false)
    private UUID equipo2;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    protected PartidoEntity() {

    }

    public PartidoEntity(UUID id, UUID equipo1, UUID equipo2, Date fecha) {
        this.id = id;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.fecha = fecha;
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getEquipo1() {
        return this.equipo1;
    }

    public UUID getEquipo2() {
        return this.equipo2;
    }

    public Date getFecha() {
        return this.fecha;
    }

}

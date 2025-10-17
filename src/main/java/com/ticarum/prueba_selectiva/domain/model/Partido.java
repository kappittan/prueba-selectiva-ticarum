package com.ticarum.prueba_selectiva.domain.model;

import java.util.Date;
import java.util.UUID;

public class Partido extends Entity {

    private UUID equipo1;
    private UUID equipo2;
    private Date fecha;

    public Partido(UUID id, UUID equipo1, UUID equipo2, Date fecha) {
        super(id);
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.fecha = fecha;
    }

    public Partido(UUID equipo1, UUID equipo2, Date fecha) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.fecha = fecha;
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

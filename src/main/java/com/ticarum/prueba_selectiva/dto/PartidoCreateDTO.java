package com.ticarum.prueba_selectiva.dto;

import java.util.Date;
import java.util.UUID;

public class PartidoCreateDTO {

    private UUID equipo1;

    private UUID equipo2;

    private Date fecha;

    PartidoCreateDTO() {
    }

    public PartidoCreateDTO(UUID equipo1, UUID equipo2, Date fecha) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.fecha = fecha;
    }

    public UUID getEquipo1() {
        return equipo1;
    }

    public UUID getEquipo2() {
        return equipo2;
    }

    public Date getFecha() {
        return fecha;
    }
}

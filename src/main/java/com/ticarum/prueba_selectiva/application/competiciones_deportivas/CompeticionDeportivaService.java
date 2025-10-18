package com.ticarum.prueba_selectiva.application.competiciones_deportivas;

import java.util.List;
import java.util.UUID;

import com.ticarum.prueba_selectiva.domain.model.CompeticionDeportiva;
import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.dto.CompeticionDeportivaCreateDTO;
import com.ticarum.prueba_selectiva.dto.JornadaDTO;

public interface CompeticionDeportivaService {
    CompeticionDeportiva añadirCompeticion(CompeticionDeportivaCreateDTO competicionValues);

    CompeticionDeportiva obtenerCompeticion(UUID competicionId);

    CompeticionDeportiva añadirEquipo(UUID competicionId, UUID equipoId);

    boolean competicionExiste(UUID competicionId);

    List<Equipo> consultarEquipos(UUID competicionId);

    void generarPrimeraJornada(UUID competicionId);

    JornadaDTO obtenerPrimeraJornada(UUID competicionId);

    List<Equipo> obtenerEquiposNoAsignadosAJornada(UUID competicionId);
}

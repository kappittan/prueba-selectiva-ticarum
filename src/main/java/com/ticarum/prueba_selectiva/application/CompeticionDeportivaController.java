package com.ticarum.prueba_selectiva.application;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticarum.prueba_selectiva.domain.model.CompeticionDeportiva;
import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.dto.CompeticionDeportivaCreateDTO;
import com.ticarum.prueba_selectiva.dto.EquipoCreateDTO;
import com.ticarum.prueba_selectiva.dto.JornadaDTO;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/competiciones_deportivas")
public class CompeticionDeportivaController {

    @Autowired
    private CompeticionDeportivaService competicionDeportivaService;

    // [Endpoint] POST /competiciones_deportivas
    @PostMapping()
    ResponseEntity<CompeticionDeportiva> añadirCompeticion(
            @Valid @RequestBody CompeticionDeportivaCreateDTO nuevaCompeticion) {

        return ResponseEntity.ok(competicionDeportivaService.añadirCompeticion(nuevaCompeticion));
    }

    // [Endpoint] GET /competiciones_deportivas/{id}
    @GetMapping("{id}")
    ResponseEntity<CompeticionDeportiva> obtenerCompeticion(@PathVariable UUID id) {
        return ResponseEntity.ok(competicionDeportivaService.obtenerCompeticion(id));
    }

    // [Endpoint] POST /competiciones_deportivas/{id}/equipos
    @PostMapping("{id}/equipos")
    ResponseEntity<CompeticionDeportiva> añadirEquipo(@PathVariable UUID id,
            @Valid @RequestBody EquipoCreateDTO equipoValues) {
        return ResponseEntity.ok(competicionDeportivaService.añadirEquipo(id, equipoValues));
    }

    // [Endpoint] GET /competiciones_deportivas/{id}/equipos
    @GetMapping("{id}/equipos")
    ResponseEntity<List<Equipo>> consultarEquipos(@PathVariable UUID id) {
        return ResponseEntity.ok(competicionDeportivaService.consultarEquipos(id));
    }

    // [Endpoint] POST /competiciones_deportivas/{id}/generar_primera_jornada
    @PostMapping("{id}/generar_primera_jornada")
    ResponseEntity<Void> generarPrimeraJornada(@PathVariable UUID id) {
        competicionDeportivaService.generarPrimeraJornada(id);
        return ResponseEntity.ok().build();
    }

    // [Endpoint] GET /competiciones_deportivas/{id}/jornadas/1
    @GetMapping("{id}/jornadas/1")
    ResponseEntity<JornadaDTO> obtenerPrimeraJornada(@PathVariable UUID id) {
        return ResponseEntity.ok(competicionDeportivaService.obtenerPrimeraJornada(id));
    }

}

package com.ticarum.prueba_selectiva.application.equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.dto.EquipoCreateDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    // [Endpoint] POST /equipos
    @PostMapping()
    ResponseEntity<Equipo> crearEquipo(
            @Valid @RequestBody EquipoCreateDTO equipoValues) {
        return ResponseEntity.ok(
                equipoService.crearEquipo(equipoValues));
    }
}

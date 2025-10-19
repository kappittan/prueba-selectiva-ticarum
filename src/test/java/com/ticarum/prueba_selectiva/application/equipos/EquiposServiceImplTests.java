package com.ticarum.prueba_selectiva.application.equipos;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.dto.EquipoCreateDTO;
import com.ticarum.prueba_selectiva.exceptions.equipos.ExcepcionEquipoNoEncontrado;
import com.ticarum.prueba_selectiva.exceptions.equipos.ExcepcionNombreEquipoNoDisponible;
import com.ticarum.prueba_selectiva.infra.EquipoRepository;
import com.ticarum.prueba_selectiva.infra.persistence.EquipoEntity;

@ExtendWith(MockitoExtension.class)
public class EquiposServiceImplTests {

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoServiceImpl equipoService;

    private EquipoCreateDTO equipoCreateDTO;

    private EquipoEntity equipoEntity;

    private Equipo equipo;

    @BeforeEach
    public void setUp() {
        UUID equipoId = UUID.randomUUID();
        String nombre = "Equipo Test";
        Optional<List<UUID>> competicionesActivas = Optional
                .of(new ArrayList<>(List.of(UUID.randomUUID(), UUID.randomUUID())));

        this.equipoCreateDTO = new EquipoCreateDTO(nombre);
        this.equipoEntity = new EquipoEntity(equipoId, nombre, competicionesActivas.get());
        this.equipo = new Equipo(equipoId, nombre, competicionesActivas);
    }

    @Test
    public void EquipoService_CrearEquipo_DevuelveEquipo() {
        when(equipoRepository.findByNombre(equipoCreateDTO.getNombre())).thenReturn(new ArrayList<>());
        when(equipoRepository.save(Mockito.any(EquipoEntity.class))).thenReturn(equipoEntity);

        Equipo resultado = equipoService.crearEquipo(equipoCreateDTO);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).isEqualTo(equipo);
    }

    @Test
    public void EquipoService_CrearEquipo_DevuelveExcepcionNombreEquipoNoDisponible() {
        when(equipoRepository.findByNombre(equipoCreateDTO.getNombre()))
                .thenReturn(new ArrayList<>(List.of(equipoEntity)));

        Assertions.assertThatThrownBy(() -> {
            equipoService.crearEquipo(equipoCreateDTO);
        }).isInstanceOf(ExcepcionNombreEquipoNoDisponible.class);
    }

    @Test
    public void EquipoService_GetEquipo_DevuelveEquipo() {
        when(equipoRepository.findById(equipo.getId())).thenReturn(Optional.of(equipoEntity));

        Equipo resultado = equipoService.getEquipo(equipo.getId());

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).isEqualTo(equipo);
    }

    @Test
    public void EquipoService_GetEquipo_DevuelveExcepcionEquipoNoEncontrado() {
        UUID equipoIdInexistente = UUID.randomUUID();
        when(equipoRepository.findById(equipoIdInexistente)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            equipoService.getEquipo(equipoIdInexistente);
        }).isInstanceOf(ExcepcionEquipoNoEncontrado.class);
    }

    @Test
    public void EquipoService_AñadirEquipoACompeticion_DevuelveEquipo() {
        UUID competicionId = UUID.randomUUID();
        when(equipoRepository.findById(equipo.getId())).thenReturn(Optional.of(equipoEntity));
        equipoEntity.getCompeticionesActivas().add(competicionId);
        when(equipoRepository.save(Mockito.any(EquipoEntity.class))).thenReturn(equipoEntity);

        Equipo resultado = equipoService.añadirEquipoACompeticion(equipo.getId(), competicionId);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getCompeticionesActivas().get()).contains(competicionId);
    }

    @Test
    public void EquipoService_AñadirEquipoACompeticion_DevuelveExcepcionEquipoNoEncontrado() {
        UUID equipoIdInexistente = UUID.randomUUID();
        UUID competicionId = UUID.randomUUID();
        when(equipoRepository.findById(equipoIdInexistente)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            equipoService.añadirEquipoACompeticion(equipoIdInexistente, competicionId);
        }).isInstanceOf(ExcepcionEquipoNoEncontrado.class);
    }
}

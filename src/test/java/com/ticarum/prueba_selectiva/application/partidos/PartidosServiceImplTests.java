package com.ticarum.prueba_selectiva.application.partidos;

import static org.mockito.Mockito.when;

import java.util.Date;
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

import com.ticarum.prueba_selectiva.domain.model.Partido;
import com.ticarum.prueba_selectiva.dto.PartidoCreateDTO;
import com.ticarum.prueba_selectiva.exceptions.partidos.ExcepcionPartidoNoEncontrado;
import com.ticarum.prueba_selectiva.infra.PartidoRepository;
import com.ticarum.prueba_selectiva.infra.persistence.PartidoEntity;

@ExtendWith(MockitoExtension.class)
public class PartidosServiceImplTests {

    @Mock
    private PartidoRepository partidoRepository;

    @InjectMocks
    private PartidoServiceImpl partidoService;

    private PartidoCreateDTO partidoDTO;

    private Partido partido;

    private PartidoEntity partidoEntity;

    @BeforeEach
    public void setUp() {
        UUID partidoId = UUID.randomUUID();
        UUID equipo1Id = UUID.randomUUID();
        UUID equipo2Id = UUID.randomUUID();
        Date fecha = new Date();

        this.partido = new Partido(partidoId, equipo1Id, equipo2Id, fecha);
        this.partidoEntity = new PartidoEntity(partidoId, equipo1Id, equipo2Id, fecha);
        this.partidoDTO = new PartidoCreateDTO(equipo1Id, equipo2Id, fecha);
    }

    @Test
    public void PartidoService_AñadirPartido_DevuelvePartido() {
        when(partidoRepository.save(Mockito.any(PartidoEntity.class))).thenReturn(partidoEntity);

        Partido resultado = partidoService.añadirPartido(partidoDTO);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).isEqualTo(partido);
    }

    @Test
    public void PartidoService_ObtenerPartido_DevuelvePartido() {
        when(partidoRepository.findById(partido.getId())).thenReturn(Optional.of(partidoEntity));

        Partido resultado = partidoService.obtenerPartido(partido.getId());

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).isEqualTo(partido);
    }

    @Test
    public void PartidoService_ObtenerPartido_DevuelveExcepcionPartidoNoEncontrado() {
        UUID partidoIdInexistente = UUID.randomUUID();
        when(partidoRepository.findById(partidoIdInexistente)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            partidoService.obtenerPartido(partidoIdInexistente);
        }).isInstanceOf(ExcepcionPartidoNoEncontrado.class);

    }
}

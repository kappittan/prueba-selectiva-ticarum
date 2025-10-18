package com.ticarum.prueba_selectiva.application.jornadas;

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

import com.ticarum.prueba_selectiva.domain.model.Jornada;
import com.ticarum.prueba_selectiva.dto.JornadaCreateDTO;
import com.ticarum.prueba_selectiva.exceptions.jornadas.ExcepcionJornadaNoEncontrada;
import com.ticarum.prueba_selectiva.infra.JornadaRepository;
import com.ticarum.prueba_selectiva.infra.persistence.JornadaEntity;

@ExtendWith(MockitoExtension.class)
public class JornadasServiceImplTests {

    @Mock
    private JornadaRepository jornadaRepository;

    @InjectMocks
    private JornadaServiceImpl jornadaService;

    private JornadaCreateDTO jornadaCreateDTO;

    private Jornada jornada;

    private JornadaEntity jornadaEntity;

    @BeforeEach
    public void setUp() {
        UUID jornadaId = UUID.randomUUID();
        UUID competicionId = UUID.randomUUID();
        List<UUID> partidosIds = new ArrayList<>(List.of(UUID.randomUUID(), UUID.randomUUID()));
        Optional<List<UUID>> equiposNoAsignados = Optional
                .of(new ArrayList<>(List.of(UUID.randomUUID(), UUID.randomUUID())));

        this.jornada = new Jornada(jornadaId, competicionId, partidosIds, equiposNoAsignados);
        this.jornadaCreateDTO = new JornadaCreateDTO(competicionId, partidosIds, equiposNoAsignados);
        this.jornadaEntity = new JornadaEntity(jornadaId, competicionId, partidosIds, equiposNoAsignados.get());
    }

    @Test
    public void JornadaService_AñadirJornada_DevuelveJornada() {
        when(jornadaRepository.save(Mockito.any(JornadaEntity.class))).thenReturn(jornadaEntity);

        Jornada resultado = jornadaService.añadirJornada(jornadaCreateDTO);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).isEqualTo(jornada);
    }

    @Test
    public void JornadaService_ObtenerJornada_DevuelveJornada() {
        when(jornadaRepository.findById(jornada.getId())).thenReturn(Optional.of(jornadaEntity));

        Jornada resultado = jornadaService.obtenerJornada(jornada.getId());

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).isEqualTo(jornada);
    }

    @Test
    public void JornadaService_ObtenerJornada_DevuelveExcepcionJornadaNoEncontrada() {
        UUID jornadaIdInexistente = UUID.randomUUID();
        when(jornadaRepository.findById(jornadaIdInexistente)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            jornadaService.obtenerJornada(jornadaIdInexistente);
        }).isInstanceOf(ExcepcionJornadaNoEncontrada.class);
    }

}

package com.ticarum.prueba_selectiva.application.competiciones_deportivas;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
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

import com.ticarum.prueba_selectiva.application.equipos.EquipoService;
import com.ticarum.prueba_selectiva.application.jornadas.JornadaService;
import com.ticarum.prueba_selectiva.application.partidos.PartidoService;
import com.ticarum.prueba_selectiva.domain.model.CompeticionDeportiva;
import com.ticarum.prueba_selectiva.domain.model.Deporte;
import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.domain.model.Jornada;
import com.ticarum.prueba_selectiva.domain.model.Partido;
import com.ticarum.prueba_selectiva.dto.CompeticionDeportivaCreateDTO;
import com.ticarum.prueba_selectiva.dto.JornadaCreateDTO;
import com.ticarum.prueba_selectiva.dto.JornadaDTO;
import com.ticarum.prueba_selectiva.dto.PartidoCreateDTO;
import com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionCompeticionNoEncontrada;
import com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionNoExistenEquiposRegistrados;
import com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionPrimeraJornadaYaGenerada;
import com.ticarum.prueba_selectiva.infra.CompeticionDeportivaRepository;
import com.ticarum.prueba_selectiva.infra.persistence.CompeticionDeportivaEntity;
import com.ticarum.prueba_selectiva.infra.persistence.EquipoEntity;
import com.ticarum.prueba_selectiva.infra.persistence.JornadaEntity;
import com.ticarum.prueba_selectiva.infra.persistence.PartidoEntity;

@ExtendWith(MockitoExtension.class)
public class CompeticionesDeportivasImplTests {

        @Mock
        private CompeticionDeportivaRepository competicionDeportivaRepository;

        @Mock
        private JornadaService jornadaService;

        @Mock
        private PartidoService partidoService;

        @Mock
        private EquipoService equipoService;

        @InjectMocks
        private CompeticionDeportivaServiceImpl competicionesDeportivasImpl;

        private CompeticionDeportivaCreateDTO competicionDTO;

        private CompeticionDeportiva competicion;

        private CompeticionDeportivaEntity competicionEntity;

        private EquipoEntity equipo1Entity;

        private EquipoEntity equipo2Entity;

        private Equipo equipo1;

        private Equipo equipo2;

        private Partido partido1;

        private PartidoEntity partido1Entity;

        private Jornada jornada1;

        private JornadaEntity jornada1Entity;

        @BeforeEach
        public void setUp() {
                // Creamos dos equipos
                UUID equipo1Id = UUID.randomUUID();
                String equipo1Nombre = "Equipo A";
                UUID equipo2Id = UUID.randomUUID();
                String equipo2Nombre = "Equipo B";
                this.equipo1 = new Equipo(equipo1Id, equipo1Nombre, Optional.empty());
                this.equipo2 = new Equipo(equipo2Id, equipo2Nombre, Optional.empty());

                // Creamos la competición deportiva
                UUID competicionId = UUID.randomUUID();
                String nombre = "Torneo de Prueba";
                Deporte deporte = Deporte.FUTBOL;
                Date fechaInicio = new Date();
                Date fechaFin = new Date(fechaInicio.getTime() + 86400000L * 5); // 5 días después
                int numPistasJornada = 2;
                Optional<List<UUID>> jornadas = Optional.of(new ArrayList<>());
                Optional<List<UUID>> equipos = Optional.of(new ArrayList<>());

                this.competicionDTO = new CompeticionDeportivaCreateDTO(nombre, deporte, fechaInicio, fechaFin,
                                numPistasJornada);
                this.competicion = new CompeticionDeportiva(competicionId, nombre, deporte, jornadas, equipos,
                                fechaInicio, fechaFin, numPistasJornada);
                this.competicionEntity = new CompeticionDeportivaEntity(competicionId, nombre, deporte, fechaInicio,
                                fechaFin, numPistasJornada, jornadas.get(), equipos.get());

                // Asociamos los equipos a la competición
                this.equipo1Entity = new EquipoEntity(equipo1Id, equipo1Nombre,
                                new ArrayList<>(List.of(competicionId)));
                this.equipo2Entity = new EquipoEntity(equipo2Id, equipo2Nombre,
                                new ArrayList<>(List.of(competicionId)));

                // Creamos el partido
                UUID partidoId = UUID.randomUUID();
                this.partido1 = new Partido(partidoId, equipo1Id, equipo2Id, fechaInicio);
                this.partido1Entity = new PartidoEntity(partidoId, equipo1Id, equipo2Id, fechaInicio);

                // Generamos la jornada
                UUID jornadaId = UUID.randomUUID();
                this.jornada1 = new Jornada(jornadaId, competicionId, List.of(partidoId), Optional.empty());
        }

        @Test
        public void CompeticionesDeportivasService_AñadirCompeticion_DevuelveCompeticion() {
                when(competicionDeportivaRepository.findByNombre(competicionDTO.getNombre()))
                                .thenReturn(new ArrayList<>());
                when(competicionDeportivaRepository.save(Mockito.any(CompeticionDeportivaEntity.class)))
                                .thenReturn(competicionEntity);

                CompeticionDeportiva resultado = competicionesDeportivasImpl.añadirCompeticion(competicionDTO);

                Assertions.assertThat(resultado).isNotNull();
                Assertions.assertThat(resultado).isEqualTo(competicion);
        }

        @Test
        public void CompeticionesDeportivasService_AñadirCompeticion_DevuelveExcepcionNombreCompeticionNoDisponible() {
                when(competicionDeportivaRepository.findByNombre(competicionDTO.getNombre()))
                                .thenReturn(new ArrayList<>(List.of(competicionEntity)));

                Assertions.assertThatThrownBy(() -> {
                        competicionesDeportivasImpl.añadirCompeticion(competicionDTO);
                }).isInstanceOf(
                                com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionNombreCompeticionNoDisponible.class);
        }

        @Test
        public void CompeticionesDeportivasService_CompeticionExiste_DevuelveTrue() {
                when(competicionDeportivaRepository.findById(competicion.getId()))
                                .thenReturn(Optional.of(competicionEntity));

                boolean resultado = competicionesDeportivasImpl.competicionExiste(competicion.getId());

                Assertions.assertThat(resultado).isTrue();
        }

        @Test
        public void CompeticionesDeportivasService_CompeticionExiste_DevuelveFalse() {
                UUID competicionIdInexistente = UUID.randomUUID();
                when(competicionDeportivaRepository.findById(competicionIdInexistente))
                                .thenReturn(Optional.empty());

                boolean resultado = competicionesDeportivasImpl.competicionExiste(competicionIdInexistente);

                Assertions.assertThat(resultado).isFalse();
        }

        @Test
        public void CompeticionesDeportivasService_ConsultarEquipos_DevuelveListaEquipos() {
                competicionEntity.getEquipos().add(equipo1.getId());
                competicionEntity.getEquipos().add(equipo2.getId());
                equipo1.añadirCompeticionActiva(competicion.getId());
                equipo2.añadirCompeticionActiva(competicion.getId());
                when(competicionDeportivaRepository.findById(competicion.getId()))
                                .thenReturn(Optional.of(competicionEntity));
                when(equipoService.getEquipo(equipo1.getId())).thenReturn(equipo1);
                when(equipoService.getEquipo(equipo2.getId())).thenReturn(equipo2);

                List<Equipo> resultado = competicionesDeportivasImpl.consultarEquipos(competicion.getId());

                Assertions.assertThat(resultado).isNotNull();
                Assertions.assertThat(resultado).isEqualTo(List.of(equipo1, equipo2));
        }

        @Test
        public void CompeticionesDeportivasService_ConsultarEquipos_DevuelveExcepcionCompeticionNoEncontrada() {
                UUID competicionIdInexistente = UUID.randomUUID();
                when(competicionDeportivaRepository.findById(competicionIdInexistente))
                                .thenReturn(Optional.empty());

                Assertions.assertThatThrownBy(() -> {
                        competicionesDeportivasImpl.consultarEquipos(competicionIdInexistente);
                }).isInstanceOf(
                                ExcepcionCompeticionNoEncontrada.class);
        }

        @Test
        public void CompeticionesDeportivasService_GenerarPrimeraJornada_GeneraPrimeraJornada() {
                competicion.registrarEquipo(equipo1.getId());
                competicion.registrarEquipo(equipo2.getId());
                when(competicionDeportivaRepository.findById(competicion.getId()))
                                .thenReturn(Optional.of(competicionEntity));
                when(partidoService.añadirPartido(Mockito.any(PartidoCreateDTO.class))).thenReturn(partido1);
                when(jornadaService.añadirJornada(Mockito.any(JornadaCreateDTO.class))).thenReturn(jornada1);
                when(competicionDeportivaRepository.save(Mockito.any(CompeticionDeportivaEntity.class)))
                                .thenReturn(competicionEntity);

                competicionesDeportivasImpl.generarPrimeraJornada(competicion.getId());

                Assertions.assertThat(competicion.getJornadas().isPresent()).isTrue();
        }

        @Test
        public void CompeticionesDeportivasService_GenerarPrimeraJornada_DevuelveExcepcionCompeticionNoEncontrada() {
                UUID competicionIdInexistente = UUID.randomUUID();
                when(competicionDeportivaRepository.findById(competicionIdInexistente))
                                .thenReturn(Optional.empty());

                Assertions.assertThatThrownBy(() -> {
                        competicionesDeportivasImpl.generarPrimeraJornada(competicionIdInexistente);
                }).isInstanceOf(
                                ExcepcionCompeticionNoEncontrada.class);
        }

        @Test
        public void CompeticionesDeportivasService_GenerarPrimeraJornada_DevuelveExcepcionPrimeraJornadaYaGenerada() {
                when(competicionDeportivaRepository.findById(competicion.getId()))
                                .thenReturn(Optional.of(competicionEntity));
                competicionEntity.getJornadas().add(jornada1.getId());

                Assertions.assertThatThrownBy(() -> {
                        competicionesDeportivasImpl.generarPrimeraJornada(competicion.getId());
                }).isInstanceOf(
                                ExcepcionPrimeraJornadaYaGenerada.class);
        }

        @Test
        public void CompeticionesDeportivasService_GenerarPrimeraJornada_DevuelveExcepcionNoExistenEquiposRegistrados() {
                when(competicionDeportivaRepository.findById(competicion.getId()))
                                .thenReturn(Optional.of(competicionEntity));

                Assertions.assertThatThrownBy(() -> {
                        competicionesDeportivasImpl.generarPrimeraJornada(competicion.getId());
                }).isInstanceOf(
                                ExcepcionNoExistenEquiposRegistrados.class);
        }

        @Test
        public void CompeticionesDeportivasService_AñadirEquipo_DevuelveCompeticionDeportiva() {
                equipo1.añadirCompeticionActiva(competicion.getId());
                CompeticionDeportivaEntity competicionEntityConEquipo = new CompeticionDeportivaEntity(
                                competicionEntity.getId(),
                                competicionEntity.getNombre(),
                                competicionEntity.getDeporte(),
                                competicionEntity.getFechaInicio(),
                                competicionEntity.getFechaFin(),
                                competicionEntity.getNumPistasJornada(),
                                competicionEntity.getJornadas(),
                                new ArrayList<>(List.of(equipo1.getId())));
                when(competicionDeportivaRepository.findById(competicion.getId()))
                                .thenReturn(Optional.of(competicionEntity));
                when(equipoService.añadirEquipoACompeticion(equipo1.getId(), competicion.getId())).thenReturn(equipo1);
                when(competicionDeportivaRepository.save(Mockito.any(CompeticionDeportivaEntity.class)))
                                .thenReturn(competicionEntityConEquipo);

                CompeticionDeportiva resultado = competicionesDeportivasImpl.añadirEquipo(competicion.getId(),
                                equipo1.getId());

                Assertions.assertThat(resultado).isNotNull();
                Assertions.assertThat(resultado.getEquipos().isPresent()).isTrue();
                Assertions.assertThat(resultado.getEquipos().get()).contains(equipo1.getId());
        }

        @Test
        public void CompeticionesDeportivasService_AñadirEquipo_DevuelveExcepcionCompeticionNoEncontrada() {
                UUID competicionIdInexistente = UUID.randomUUID();
                when(competicionDeportivaRepository.findById(competicionIdInexistente))
                                .thenReturn(Optional.empty());

                Assertions.assertThatThrownBy(() -> {
                        competicionesDeportivasImpl.añadirEquipo(competicionIdInexistente, equipo1.getId());
                }).isInstanceOf(
                                ExcepcionCompeticionNoEncontrada.class);
        }

        @Test
        public void CompeticionesDeportivasService_ObtenerPrimeraJornada_DevuelvePrimeraJornada() {
                competicionEntity.getJornadas().add(jornada1.getId());
                when(competicionDeportivaRepository.findById(competicion.getId()))
                                .thenReturn(Optional.of(competicionEntity));
                when(jornadaService.obtenerJornada(jornada1.getId())).thenReturn(jornada1);
                when(partidoService.obtenerPartido(partido1.getId())).thenReturn(partido1);
                JornadaDTO jornadaDTO = new JornadaDTO(List.of(partido1), List.of());

                JornadaDTO resultado = competicionesDeportivasImpl.obtenerPrimeraJornada(competicion.getId());

                Assertions.assertThat(resultado).isNotNull();
                Assertions.assertThat(resultado.getPartidos()).isEqualTo(jornadaDTO.getPartidos());
                Assertions.assertThat(resultado.getEquiposNoAsignados()).isEqualTo(jornadaDTO.getEquiposNoAsignados());
        }

        @Test
        public void CompeticionDeportivaService_ObtnerPrimeraJornada_ExcepcionCompeticionNoEncontrada() {
                UUID competicionIdInexistente = UUID.randomUUID();
                when(competicionDeportivaRepository.findById(competicionIdInexistente))
                                .thenReturn(Optional.empty());

                Assertions.assertThatThrownBy(() -> {
                        competicionesDeportivasImpl.obtenerPrimeraJornada(competicionIdInexistente);
                }).isInstanceOf(
                                ExcepcionCompeticionNoEncontrada.class);
        }

        @Test
        public void CompeticionDeportivaService_ObtnerPrimeraJornada_ExcepcionPrimeraJornadaNoGenerada() {
                when(competicionDeportivaRepository.findById(competicion.getId()))
                                .thenReturn(Optional.of(competicionEntity));

                Assertions.assertThatThrownBy(() -> {
                        competicionesDeportivasImpl.obtenerPrimeraJornada(competicion.getId());
                }).isInstanceOf(
                                com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionPrimeraJornadaNoGenerada.class);
        }

        @Test
        public void CompeticionDeportivaService_ObtenerCompeticion_DevuelveCompeticion() {
                when(competicionDeportivaRepository.findById(competicion.getId()))
                                .thenReturn(Optional.of(competicionEntity));

                CompeticionDeportiva resultado = competicionesDeportivasImpl.obtenerCompeticion(competicion.getId());

                Assertions.assertThat(resultado).isNotNull();
                Assertions.assertThat(resultado).isEqualTo(competicion);
        }

        @Test
        public void CompeticionDeportivaService_ObtenerCompeticion_ExcepcionCompeticionNoEncontrada() {
                UUID competicionIdInexistente = UUID.randomUUID();
                when(competicionDeportivaRepository.findById(competicionIdInexistente))
                                .thenReturn(Optional.empty());

                Assertions.assertThatThrownBy(() -> {
                        competicionesDeportivasImpl.obtenerCompeticion(competicionIdInexistente);
                }).isInstanceOf(
                                ExcepcionCompeticionNoEncontrada.class);
        }

}

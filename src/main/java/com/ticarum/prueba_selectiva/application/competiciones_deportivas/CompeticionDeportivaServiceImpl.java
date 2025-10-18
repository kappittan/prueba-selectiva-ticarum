package com.ticarum.prueba_selectiva.application.competiciones_deportivas;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticarum.prueba_selectiva.application.equipos.EquipoService;
import com.ticarum.prueba_selectiva.application.jornadas.JornadaService;
import com.ticarum.prueba_selectiva.application.partidos.PartidoService;
import com.ticarum.prueba_selectiva.domain.model.CompeticionDeportiva;
import com.ticarum.prueba_selectiva.domain.model.Equipo;
import com.ticarum.prueba_selectiva.domain.model.Jornada;
import com.ticarum.prueba_selectiva.domain.model.Partido;
import com.ticarum.prueba_selectiva.dto.CompeticionDeportivaCreateDTO;
import com.ticarum.prueba_selectiva.dto.JornadaCreateDTO;
import com.ticarum.prueba_selectiva.dto.JornadaDTO;
import com.ticarum.prueba_selectiva.dto.PartidoCreateDTO;
import com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionCompeticionNoEncontrada;
import com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionNoExistenEquiposRegistrados;
import com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionNombreCompeticionNoDisponible;
import com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionPrimeraJornadaNoGenerada;
import com.ticarum.prueba_selectiva.exceptions.competiciones_deportivas.ExcepcionPrimeraJornadaYaGenerada;
import com.ticarum.prueba_selectiva.infra.CompeticionDeportivaRepository;
import com.ticarum.prueba_selectiva.infra.persistence.CompeticionDeportivaEntity;
import com.ticarum.prueba_selectiva.mapper.CompeticionDeportivaMapper;

@Service
public class CompeticionDeportivaServiceImpl implements CompeticionDeportivaService {

    @Autowired
    private CompeticionDeportivaRepository competicionDeportivaRepository;

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private JornadaService jornadaService;

    @Autowired
    private PartidoService partidoService;

    @Override
    public CompeticionDeportiva añadirCompeticion(CompeticionDeportivaCreateDTO competicionValues) {

        // 1. Comprobramos que el nombre no esté ya en uso
        if (competicionDeportivaRepository.findByNombre(competicionValues.getNombre()).size() > 0) {
            throw new ExcepcionNombreCompeticionNoDisponible();
        }

        // 2. Creamos la competición deportiva en el dominio
        CompeticionDeportiva competicion = new CompeticionDeportiva(
                competicionValues.getNombre(),
                competicionValues.getDeporte(),
                competicionValues.getFechaInicio(),
                competicionValues.getFechaFin(),
                competicionValues.getNumPistasJornada());

        // 3. Guardamos la competición deportiva en la base de datos
        CompeticionDeportivaEntity entidad = competicionDeportivaRepository
                .save(CompeticionDeportivaMapper.toPersistence(competicion));

        // 4. Devolvemos la competición deportiva guardada convertida a dominio
        return CompeticionDeportivaMapper.toDomain(entidad);
    }

    @Override
    public boolean competicionExiste(UUID competicionId) {
        // Verificamos si la competición deportiva existe en la base de datos
        if (competicionDeportivaRepository.findById(competicionId).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Equipo> consultarEquipos(UUID competicionId) {

        // 1. Validamos que la competición exista
        if (!this.competicionExiste(competicionId)) {
            throw new ExcepcionCompeticionNoEncontrada(competicionId);
        }

        // 2. Obtenemos la competición deportiva del repositorio, y la mapeamos a
        // dominio
        CompeticionDeportiva competicion = CompeticionDeportivaMapper
                .toDomain(competicionDeportivaRepository.findById(competicionId).get());

        // 3. Recuperamos la lista de equipos asociados a la competición deportiva
        List<UUID> equiposIds = competicion.getEquipos().orElse(List.of());

        // 4. Mapeamos los equipos a dominio y los devolvemos
        return equiposIds.stream()
                .map(equipoId -> equipoService.getEquipo(equipoId))
                .toList();
    }

    @Override
    public void generarPrimeraJornada(UUID competicionId) {

        // 1. Validamos si la competición existe
        if (!this.competicionExiste(competicionId)) {
            throw new ExcepcionCompeticionNoEncontrada(competicionId);
        }

        // 2. Tras comprobar que existe, la recuperamos de la base de datos
        CompeticionDeportiva competicion = CompeticionDeportivaMapper
                .toDomain(competicionDeportivaRepository.findById(competicionId).get());

        // 3. Comprobamos si la primera jornada ya ha sido generada
        if (primeraJornadaGenerada(competicion)) {
            throw new ExcepcionPrimeraJornadaYaGenerada();
        }

        // 4. Obtenemos los datos necesarios del objeto competición para generar la
        // primera jornada. La lista de equipos se aleatoriza para que los
        // emparejamientos sean aleatorios y no por orden de inserción
        List<UUID> equiposIds = competicion.getEquipos().orElse(List.of());
        Collections.shuffle(equiposIds);

        LocalDate fechaInicio = competicion.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFin = competicion.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int numPistasJornada = competicion.getNumPistasJornada();

        // 5. Emparejamos los equipos. Aquí iteramos los días que dura la competición, y
        // dentro de cada día, según el número de pistas disponibles (Cada pista puede
        // utilizarse dos veces al día). En cada iteración vamos extrayendo parejas de
        // equipos de la lista y generando partidos.
        List<UUID> partidos = new ArrayList<>();

        for (LocalDate fecha = fechaInicio; (fecha.isBefore(fechaFin) || fecha.isEqual(fechaFin)); fecha = fecha
                .plusDays(1)) {
            for (int pista = 0; pista < (numPistasJornada * 2); pista++) {

                // Si no quedan equipos por emparejar, salimos del bucle
                if (equiposIds.size() == 0) {
                    break;
                }

                // Si queda un único equipo sin emparejar, salimos
                if (equiposIds.size() < 2 && equiposIds.size() > 0) {
                    break;
                }

                UUID equipo1 = equiposIds.remove(0);
                UUID equipo2 = equiposIds.remove(0);

                Date fechaPartido = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Partido partido = partidoService.añadirPartido(new PartidoCreateDTO(equipo1, equipo2, fechaPartido));

                partidos.add(partido.getId());
            }
        }

        // 6. Si no se ha generado ningún partido, lo cual puede ocurrir o bien por
        // valores extremos de las variables días y pistas, o bien porque no hay equipos
        // asignados a la competición; no se debe generar la nueva jornada y se devuelve
        // un error.
        // Los valores extremos de las variables días y pistas se controlan en el DTO,
        // así que solo contemplamos el caso de que no hayan equipos registrados.
        if (partidos.size() == 0) {
            throw new ExcepcionNoExistenEquiposRegistrados(competicionId);
        }

        // 7. Creamos la jornada y la asociamos a la competición deportiva
        Jornada primeraJornada = jornadaService
                .añadirJornada(new JornadaCreateDTO(competicionId, partidos, Optional.ofNullable(equiposIds)));

        competicion.añadirPrimeraJornada(primeraJornada.getId());

        // 8. Guardamos la competición deportiva actualizada en la base de datos
        competicionDeportivaRepository
                .save(CompeticionDeportivaMapper.toPersistence(competicion));
    }

    private boolean primeraJornadaGenerada(CompeticionDeportiva competicion) {
        if (competicion.getJornadas().isPresent() && competicion.getJornadas().get().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CompeticionDeportiva añadirEquipo(UUID competicionId, UUID equipoId) {

        // 1. Validamos si la competición existe
        if (!this.competicionExiste(competicionId)) {
            throw new ExcepcionCompeticionNoEncontrada(competicionId);
        }

        // 2. Obtenemos la competición deportiva del repositorio, y la mapeamos a
        // dominio
        CompeticionDeportiva competicion = CompeticionDeportivaMapper
                .toDomain(competicionDeportivaRepository.findById(competicionId).get());

        // 3. Actualizamos la competición para añadir el nuevo equipo
        competicion.registrarEquipo(equipoId);

        // 4. Actualizamos el equipo para añadir la competición deportiva
        equipoService.añadirEquipoACompeticion(equipoId, competicionId);

        // 5. Guardamos la competición deportiva actualizada en la base de datos
        CompeticionDeportivaEntity entidadActualizada = competicionDeportivaRepository
                .save(CompeticionDeportivaMapper.toPersistence(competicion));

        // 6. Devolvemos la competición deportiva actualizada convertida a dominio
        return CompeticionDeportivaMapper.toDomain(entidadActualizada);
    }

    @Override
    public JornadaDTO obtenerPrimeraJornada(UUID competicionId) {
        // 1. Validamos si la competición existe
        if (!this.competicionExiste(competicionId)) {
            throw new ExcepcionCompeticionNoEncontrada(competicionId);
        }

        // 2. Obtemos la competición deportiva del repositorio
        CompeticionDeportiva competicion = CompeticionDeportivaMapper
                .toDomain(competicionDeportivaRepository.findById(competicionId).get());

        // 3. Comprobamos si la primera jornada ha sido generada
        if (!primeraJornadaGenerada(competicion)) {
            throw new ExcepcionPrimeraJornadaNoGenerada(competicionId);
        }

        // 4. Obtenemos la primera jornada
        Jornada primeraJornada = jornadaService.obtenerJornada(competicion.getJornadas().get().get(0));

        // 5. Obtenemos los partidos de la primera jornada
        List<Partido> partidosPrimeraJornada = primeraJornada.getPartidos().stream()
                .map(partidoId -> partidoService.obtenerPartido(partidoId))
                .toList();

        // 6. Obtenemos los equipos no asignados de la primera jornada
        List<Equipo> equiposNoAsignados = primeraJornada.getEquiposNoAsignados().isPresent()
                ? primeraJornada.getEquiposNoAsignados().get().stream()
                        .map(equipoId -> equipoService.getEquipo(equipoId))
                        .toList()
                : List.of();

        // 7. Construimos el DTO y devolvemos
        return new JornadaDTO(partidosPrimeraJornada, equiposNoAsignados);
    }

    @Override
    public CompeticionDeportiva obtenerCompeticion(UUID competicionId) {
        // 1. Validamos si la competición existe
        if (!this.competicionExiste(competicionId)) {
            System.out.println("No existe la competicion con id " + competicionId);
            throw new ExcepcionCompeticionNoEncontrada(competicionId);
        }

        // 2. Obtenemos la competición deportiva del repositorio
        CompeticionDeportivaEntity entidad = competicionDeportivaRepository.findById(competicionId).get();

        // 3. Devolvemos la competición deportiva convertida a dominio
        return CompeticionDeportivaMapper.toDomain(entidad);
    }

    @Override
    public List<Equipo> obtenerEquiposNoAsignadosAJornada(UUID competicionId) {
        // 1. Validamos si la competición existe
        if (!this.competicionExiste(competicionId)) {
            throw new ExcepcionCompeticionNoEncontrada(competicionId);
        }

        // 2. Obtemos la competición deportiva del repositorio
        CompeticionDeportiva competicion = CompeticionDeportivaMapper
                .toDomain(competicionDeportivaRepository.findById(competicionId).get());

        // 3. Comprobamos si la primera jornada ha sido generada
        if (!primeraJornadaGenerada(competicion)) {
            throw new ExcepcionPrimeraJornadaNoGenerada(competicionId);
        }

        // 4. Obtenemos la primera jornada
        Jornada primeraJornada = jornadaService.obtenerJornada(competicion.getJornadas().get().get(0));

        // 5. Obtenemos los equipos no asignados de la primera jornada
        List<UUID> equiposNoAsignadosIds = primeraJornada.getEquiposNoAsignados().orElse(List.of());

        // 6. Mapeamos los equipos a dominio y los devolvemos
        return equiposNoAsignadosIds.stream()
                .map(equipoId -> equipoService.getEquipo(equipoId))
                .toList();
    }

}

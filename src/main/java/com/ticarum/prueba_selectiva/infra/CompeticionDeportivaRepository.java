package com.ticarum.prueba_selectiva.infra;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ticarum.prueba_selectiva.infra.persistence.CompeticionDeportivaEntity;
import java.util.List;

public interface CompeticionDeportivaRepository extends JpaRepository<CompeticionDeportivaEntity, UUID> {
    List<CompeticionDeportivaEntity> findByNombre(String nombre);
}

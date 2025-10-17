package com.ticarum.prueba_selectiva.infra;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ticarum.prueba_selectiva.infra.persistence.EquipoEntity;
import java.util.List;

public interface EquipoRepository extends JpaRepository<EquipoEntity, UUID> {

    List<EquipoEntity> findByNombre(String nombre);
}

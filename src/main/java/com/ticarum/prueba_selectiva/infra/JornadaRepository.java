package com.ticarum.prueba_selectiva.infra;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ticarum.prueba_selectiva.infra.persistence.JornadaEntity;

public interface JornadaRepository extends JpaRepository<JornadaEntity, UUID> {

}

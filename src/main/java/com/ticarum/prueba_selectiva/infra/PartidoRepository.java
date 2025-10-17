package com.ticarum.prueba_selectiva.infra;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ticarum.prueba_selectiva.infra.persistence.PartidoEntity;

public interface PartidoRepository extends JpaRepository<PartidoEntity, UUID> {

}

package com.ticarum.prueba_selectiva.domain.model;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity {

    protected final UUID id;

    protected Entity(UUID id) {
        this.id = id != null ? id : UUID.randomUUID();
    }

    protected Entity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }
}

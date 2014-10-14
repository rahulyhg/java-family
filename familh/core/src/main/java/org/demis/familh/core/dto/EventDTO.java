package org.demis.familh.core.dto;

import org.demis.familh.core.jpa.entity.EventType;

public class EventDTO implements DTO {

    private Long id;
    private EventType type;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}

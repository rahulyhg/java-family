package org.demis.familh.core.converter;

import org.demis.familh.core.dto.EventDTO;
import org.demis.familh.core.jpa.entity.Event;

public class EventConverter extends GenericConverter<Event, EventDTO> {
    public EventConverter() {
        super(Event.class, EventDTO.class);
    }

    @Override
    protected void updateModelFields(Event event, EventDTO eventDTO) {
    }

    @Override
    protected void updateDTOFields(EventDTO eventDTO, Event event) {
    }

}

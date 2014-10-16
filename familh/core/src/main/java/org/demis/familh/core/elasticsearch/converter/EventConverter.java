package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.elasticsearch.dto.EventDTO;
import org.demis.familh.core.jpa.entity.Event;
import org.springframework.stereotype.Service;

@Service(value = "eventConverterES")
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

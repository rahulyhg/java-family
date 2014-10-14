package org.demis.familh.web.converter;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.web.dto.EventDTOWeb;
import org.springframework.stereotype.Service;

@Service (value = "eventConverterWeb")
public class EventConverterWeb extends GenericConverterWeb<Event, EventDTOWeb> {

    public EventConverterWeb() {
        super(Event.class, EventDTOWeb.class);
    }

    protected void updateModelFields(Event event, EventDTOWeb eventDTO) {
        event.setType(eventDTO.getType());
    }

    protected void updateDTOFields(EventDTOWeb eventDTO, Event event) {
        eventDTO.setType(event.getType());
        eventDTO.setPersonId(event.getPerson().getId());
    }

}

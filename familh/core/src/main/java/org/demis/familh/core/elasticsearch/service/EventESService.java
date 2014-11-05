package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.elasticsearch.converter.EventConverter;
import org.demis.familh.core.elasticsearch.dto.EventDTO;
import org.demis.familh.core.jpa.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "eventESService")
public class EventESService extends ESService<Event, EventDTO> {

    public static final String USER_MAPPING = "event";

    @Autowired
    @Qualifier("eventConverterES")
    private EventConverter eventConverter;

    @Override
    protected EventConverter getConverter() {
        return eventConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }
}
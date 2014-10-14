package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.service.EventService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value ="eventESService")
public class EventESService implements EventService {
    @Override
    public List<Event> findPersonEvents(Person person) {
        return null;
    }

    @Override
    public Event create(Event created) {
        return null;
    }

    @Override
    public Event delete(Long id) throws ModelNotFoundException {
        return null;
    }

    @Override
    public List<Event> findAll() {
        return null;
    }

    @Override
    public List<Event> findPart(int page, int size) {
        return null;
    }

    @Override
    public Event findById(Long id) {
        return null;
    }

    @Override
    public Event update(Event updated) throws ModelNotFoundException {
        return null;
    }
}

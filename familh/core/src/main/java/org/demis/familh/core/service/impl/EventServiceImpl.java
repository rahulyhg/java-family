package org.demis.familh.core.service.impl;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("eventService")
public class EventServiceImpl implements EventService {

    @Autowired
    @Qualifier("eventRepositoryService")
    private EventService repositorySevice;

    @Autowired
    @Qualifier ("eventESService")
    private EventService elasticSearchService;


    @Override
    @Transactional
    public Event create(Event created) {
        Event event = repositorySevice.create(created);
        elasticSearchService.create(event);
        return event;
    }

    @Override
    @Transactional
    public Event delete(Long id) throws ModelNotFoundException {
        Event event = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return event;
    }

    @Override
    @Transactional
    public List<Event> findAll() {
        return repositorySevice.findAll();
    }

    @Override
    @Transactional
    public List<Event> findPart(int page, int size) {
        return repositorySevice.findPart(page, size);
    }

    @Override
    @Transactional
    public Event findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Override
    @Transactional
    public Event update(Event updated) throws ModelNotFoundException {
        Event event = repositorySevice.update(updated);
        elasticSearchService.update(event);
        return event;
    }

    @Override
    public List<Event> findPersonEvents(Person person) {
        return null;
    }
}

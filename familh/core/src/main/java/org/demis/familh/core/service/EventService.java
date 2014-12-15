package org.demis.familh.core.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.elasticsearch.service.EventESService;
import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.service.EventRepositoryService;
import org.demis.familh.core.service.EventService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service("eventService")
public class EventService {

    @Autowired
    @Qualifier("eventRepositoryService")
    private EventRepositoryService repositorySevice;

    @Autowired
    @Qualifier ("eventESService")
    private EventESService elasticSearchService;

    @Transactional
    public Event create(Event created) {
        Event event = repositorySevice.create(created);
        elasticSearchService.create(event);
        return event;
    }

    @Transactional
    public Event delete(Long id) throws ModelNotFoundException {
        Event event = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return event;
    }

    @Transactional
    public List<Event> findAll() {
        return repositorySevice.findAll();
    }

    @Transactional
    public Event findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Transactional
    public Event update(Event updated) throws ModelNotFoundException {
        Event event = repositorySevice.update(updated);
        elasticSearchService.update(event);
        return event;
    }

    @Transactional
    public List<Event> findPersonEvents(Person person, Range range, List<Sort> sorts) {
        return repositorySevice.findPersonEvents(person, range, sorts);
    }
}

package org.demis.familh.core.jpa.service;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.repository.EventRepository;
import org.demis.familh.core.service.EventService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="eventRepositoryService")
public class EventRepositoryService implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventRepositoryService.class);

    @Resource(name = "eventRepository")
    private EventRepository eventRepository;

    @Transactional
    @Override
    public Event create(Event created) {
        return eventRepository.save(created);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    @Override
    public Event delete(Long id) throws ModelNotFoundException {
        Event deleted = eventRepository.findOne(id);

        if (deleted == null) {
            logger.debug("No Event found with id: " + id);
            throw new ModelNotFoundException();
        }

        eventRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Event> findPart(int page, int size) {
        return eventRepository.findAll(new PageRequest(page, size)).getContent();
    }

    @Transactional(readOnly = true)
    @Override
    public Event findById(Long id) {
        return eventRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    @Override
    public Event update(Event updated) throws ModelNotFoundException {
        Event event = eventRepository.findOne(updated.getId());

        if (event == null) {
            logger.debug("No Event found with id: " + updated.getId());
            throw new ModelNotFoundException();
        }

        return event;
    }

    @Override
    public List<Event> findPersonEvents(Person person) {
        return eventRepository.findPersonEvents(person);
    }
}

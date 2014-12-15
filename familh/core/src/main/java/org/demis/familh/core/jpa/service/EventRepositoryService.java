package org.demis.familh.core.jpa.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.Family;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.repository.EventRepository;
import org.demis.familh.core.jpa.service.converter.SortConverter;
import org.demis.familh.core.service.EventService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="eventRepositoryService")
public class EventRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepositoryService.class);

    @Resource(name = "eventRepository")
    private EventRepository eventRepository;

    @Transactional
    public Event create(Event created) {
        return eventRepository.save(created);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public Event delete(Long id) throws ModelNotFoundException {
        Event deleted = eventRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No Event found with id: " + id);
            throw new ModelNotFoundException();
        }

        eventRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Event> findPart(int page, int size) {
        return eventRepository.findAll(new PageRequest(page, size)).getContent();
    }

    @Transactional(readOnly = true)
    public Event findById(Long id) {
        return eventRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public Event update(Event updated) throws ModelNotFoundException {
        Event event = eventRepository.findOne(updated.getId());

        if (event == null) {
            LOGGER.debug("No Event found with id: " + updated.getId());
            throw new ModelNotFoundException();
        }

        return event;
    }

    @Transactional(readOnly = true)
    public List<Event> findPersonEvents(Person person, Range range, List<Sort> sorts) {
        Page<Event> entitiesPage =  eventRepository.findPersonEvents(person, new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts)));
        if (entitiesPage != null) {
            return entitiesPage.getContent();
        }
        else {
            return null;
        }
    }
}

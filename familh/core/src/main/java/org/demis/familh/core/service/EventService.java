package org.demis.familh.core.service;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.Person;

import java.util.List;

public interface EventService extends GenericService<Event> {

    List<Event> findPersonEvents(Person person);
}

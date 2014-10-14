package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("eventRepository")
public interface  EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e, EventPersonAssociation a where e = a.event and a.person = ?1")
    List<Event> findPersonEvents(Person person);
}

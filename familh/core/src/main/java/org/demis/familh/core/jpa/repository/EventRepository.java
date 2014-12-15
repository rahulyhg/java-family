package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository("eventRepository")
public interface  EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e, EventPersonAssociation a where e = a.event and a.person = ?1")
    Page<Event> findPersonEvents(Person person, Pageable pageable);
}

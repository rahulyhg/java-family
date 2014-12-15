package org.demis.familh.core.elasticsearch.dto;

import org.demis.familh.core.dto.DTO;
import org.demis.familh.core.dto.EventDTO;
import org.demis.familh.core.dto.NameDTO;
import org.demis.familh.core.dto.PersonDTO;

import java.util.List;

public class PersonESDTO extends PersonDTO implements DTO {

    private List<NameDTO> personNames;

    private List<EventDTO> events;
   
    public PersonESDTO() {
        // no op
    }

    public List<NameDTO> getPersonNames() {
        return personNames;
    }

    public void setPersonNames(List<NameDTO> personNames) {
        this.personNames = personNames;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }
}

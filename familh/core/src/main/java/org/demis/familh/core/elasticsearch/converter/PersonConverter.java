package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.elasticsearch.dto.PersonESDTO;
import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.EventPersonAssociation;
import org.demis.familh.core.jpa.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "personConverterES")
public class PersonConverter extends GenericConverter<Person, PersonESDTO> {

    @Autowired
    @Qualifier("nameConverterES")
    private NameConverter nameConverter;

    @Autowired
    @Qualifier("eventConverterES")
    private EventConverter eventConverter;

    public PersonConverter() {
        super(Person.class, PersonESDTO.class);
    }

    @Override
    protected void updateModelFields(Person person, PersonESDTO personDTO) {
        person.setId(personDTO.getId());
        person.setSex(personDTO.getSex());
        person.setIdent(personDTO.getIdent());
        person.setAccess(personDTO.getAccess());
        person.setNames(nameConverter.convertDTOs(personDTO.getPersonNames()));

    }

    @Override
    protected void updateDTOFields(PersonESDTO personDTO, Person person) {
        personDTO.setId(person.getId());
        personDTO.setIdent(person.getIdent());
        personDTO.setSex(person.getSex());
        personDTO.setAccess(person.getAccess());
        personDTO.setPersonNames(nameConverter.convertModels(person.getNames()));

        List<Event> events = new ArrayList<Event>();
        for (EventPersonAssociation eventPersonAssociation: person.getEvents()) {
            events.add(eventPersonAssociation.getEvent());
        }

        personDTO.setEvents(eventConverter.convertModels(events));
    }
}

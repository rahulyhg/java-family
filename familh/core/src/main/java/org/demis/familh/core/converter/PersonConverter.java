package org.demis.familh.core.converter;

import org.demis.familh.core.dto.PersonDTO;
import org.demis.familh.core.jpa.entity.Person;

public class PersonConverter extends GenericConverter<Person, PersonDTO> {

    public PersonConverter() {
        super(Person.class, PersonDTO.class);
    }

    @Override
    protected void updateModelFields(Person person, PersonDTO personDTO) {
        person.setSex(personDTO.getSex());
        person.setIdent(personDTO.getIdent());
    }

    @Override
    protected void updateDTOFields(PersonDTO personDTO, Person person) {
        personDTO.setIdent(person.getIdent());
        personDTO.setSex(person.getSex());
    }
}

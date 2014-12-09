package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.elasticsearch.dto.PersonESDTO;
import org.demis.familh.core.jpa.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "personConverterES")
public class PersonConverter extends GenericConverter<Person, PersonESDTO> {

    @Autowired
    @Qualifier("nameConverterES")
    private NameConverter nameConverter;

    public PersonConverter() {
        super(Person.class, PersonESDTO.class);
    }

    @Override
    protected void updateModelFields(Person person, PersonESDTO personDTO) {
        person.setSex(personDTO.getSex());
        person.setIdent(personDTO.getIdent());
        person.setAccess(personDTO.getAccess());
        person.setNames(nameConverter.convertDTOs(personDTO.getPersonNames()));
    }

    @Override
    protected void updateDTOFields(PersonESDTO personDTO, Person person) {
        personDTO.setIdent(person.getIdent());
        personDTO.setSex(person.getSex());
        personDTO.setAccess(person.getAccess());
        personDTO.setPersonNames(nameConverter.convertModels(person.getNames()));
    }
}

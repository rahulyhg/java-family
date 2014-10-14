package org.demis.familh.web.converter;

import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.web.dto.DTOWeb;
import org.demis.familh.web.dto.PersonDTOWeb;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service(value = "personConverterWeb")
public class PersonConverterWeb extends GenericConverterWeb<Person, PersonDTOWeb> {

    public PersonConverterWeb() {
        super(Person.class, PersonDTOWeb.class);
    }

    protected void updateModelFields(Person person, PersonDTOWeb personDTO) {
        person.setSex(personDTO.getSex());
        person.setIdent(personDTO.getIdent());
    }

    protected void updateDTOFields(PersonDTOWeb personDTO, Person person) {
        personDTO.setIdent(person.getIdent());
        personDTO.setSex(person.getSex());
    }

    @Override
    public void generateHref(DTOWeb dto, HttpServletRequest request) {
        super.generateHref(dto, request);
        generateHrefForReferences(dto, request, ((PersonDTOWeb)dto).getEvents());
        generateHrefForReferences(dto, request, ((PersonDTOWeb) dto).getNames());
    }
}

package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.elasticsearch.converter.PersonConverter;
import org.demis.familh.core.elasticsearch.dto.PersonESDTO;
import org.demis.familh.core.jpa.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "personESService")
public class PersonESService extends ESService<Person, PersonESDTO> {

    public static final String USER_MAPPING = "person";

    @Autowired
    @Qualifier("personConverterES")
    private PersonConverter personConverter;

    @Override
    protected Class<PersonESDTO> getDTOClass() {
        return PersonESDTO.class;
    }

    @Override
    protected PersonConverter getConverter() {
        return personConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }
}
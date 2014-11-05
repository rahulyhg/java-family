package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.elasticsearch.converter.PersonConverter;
import org.demis.familh.core.elasticsearch.dto.PersonDTO;
import org.demis.familh.core.jpa.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "personESService")
public class PersonESService extends ESService<Person, PersonDTO> {

    public static final String USER_MAPPING = "person";

    @Autowired
    @Qualifier("personConverterES")
    private PersonConverter personConverter;

    @Override
    protected PersonConverter getConverter() {
        return personConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }
}
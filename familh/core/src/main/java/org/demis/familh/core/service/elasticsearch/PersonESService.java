package org.demis.familh.core.service.elasticsearch;

import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value ="personESService")
public class PersonESService implements PersonService {
    @Override
    public Person create(Person created) {
        return null;
    }

    @Override
    public Person delete(Long id) throws ModelNotFoundException {
        return null;
    }

    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public List<Person> findPart(int page, int size) {
        return null;
    }

    @Override
    public Person findById(Long id) {
        return null;
    }

    @Override
    public Person update(Person updated) throws ModelNotFoundException {
        return null;
    }
}

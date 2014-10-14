package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value ="nameESService")
public class NameESService implements NameService {

    @Override
    public List<Name> findPersonNames(Person person) {
        return null;
    }

    @Override
    public Name create(Name created) {
        return null;
    }

    @Override
    public Name delete(Long id) throws ModelNotFoundException {
        return null;
    }

    @Override
    public List<Name> findAll() {
        return null;
    }

    @Override
    public List<Name> findPart(int page, int size) {
        return null;
    }

    @Override
    public Name findById(Long id) {
        return null;
    }

    @Override
    public Name update(Name updated) throws ModelNotFoundException {
        return null;
    }
}

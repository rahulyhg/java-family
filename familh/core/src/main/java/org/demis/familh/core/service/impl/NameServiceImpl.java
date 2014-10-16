package org.demis.familh.core.service.impl;

import org.demis.familh.core.elasticsearch.service.NameESService;
import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("nameService")
public class NameServiceImpl implements NameService {

    @Autowired
    @Qualifier("nameRepositoryService")
    private NameService repositorySevice;

    @Autowired
    @Qualifier ("nameESService")
    private NameESService elasticSearchService;


    @Override
    @Transactional
    public Name create(Name created) {
        Name name = repositorySevice.create(created);
        elasticSearchService.create(name);
        return name;
    }

    @Override
    @Transactional
    public Name delete(Long id) throws ModelNotFoundException {
        Name name = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return name;
    }

    @Override
    @Transactional
    public List<Name> findAll() {
        return repositorySevice.findAll();
    }

    @Override
    @Transactional
    public List<Name> findPart(int page, int size) {
        return repositorySevice.findPart(page, size);
    }

    @Override
    @Transactional
    public Name findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Override
    @Transactional
    public Name update(Name updated) throws ModelNotFoundException {
        Name name = repositorySevice.update(updated);
        elasticSearchService.update(name);
        return name;
    }

    @Override
    public List<Name> findPersonNames(Person person) {
        return repositorySevice.findPersonNames(person);
    }
}

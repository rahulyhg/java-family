package org.demis.familh.core.service.impl;

import org.demis.familh.core.elasticsearch.service.PersonESService;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("personService")
public class PersonSeviceImpl extends GenericServiceImpl<Person> implements PersonService {

    @Autowired
    @Qualifier("personRepositoryService")
    private PersonService repositorySevice;

    @Autowired
    @Qualifier ("personESService")
    private PersonESService elasticSearchService;


    @Override
    @Transactional
    public Person create(Person created) {
        Person person = repositorySevice.create(created);
        elasticSearchService.create(person);
        return person;
    }

    @Override
    @Transactional
    public Person delete(Long id) throws ModelNotFoundException {
        Person person = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return person;
    }

    @Override
    @Transactional
    public List<Person> findAll() {
        return repositorySevice.findAll();
    }

    @Override
    @Transactional
    public List<Person> findPart(int page, int size) {
        return repositorySevice.findPart(page, size);
    }

    @Override
    @Transactional
    public Person findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Override
    @Transactional
    public Person update(Person updated) throws ModelNotFoundException {
        Person person = repositorySevice.update(updated);
        elasticSearchService.update(person);
        return person;
    }

    @Override
    public List<Person> findFamilyTreePersons(FamilyTree familyTree) {
        return repositorySevice.findFamilyTreePersons(familyTree);
    }
}

package org.demis.familh.core.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.elasticsearch.service.PersonESService;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.service.PersonRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("personService")
public class PersonService {

    @Autowired
    @Qualifier("personRepositoryService")
    private PersonRepositoryService repositorySevice;

    @Autowired
    @Qualifier ("personESService")
    private PersonESService elasticSearchService;

    @Transactional
    public Person create(Person created) {
        Person person = repositorySevice.create(created);
        elasticSearchService.create(person);
        return person;
    }

    @Transactional
    public Person delete(Long id) throws ModelNotFoundException {
        Person person = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return person;
    }

    @Transactional
    public List<Person> findAll() {
        return repositorySevice.findAll();
    }

    @Transactional
    public List<Person> findPart(int page, int size) {
        return repositorySevice.findPart(page, size);
    }

    @Transactional
    public Person findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Transactional
    public Person update(Person updated) throws ModelNotFoundException {
        Person person = repositorySevice.update(updated);
        elasticSearchService.update(person);
        return person;
    }

    @Transactional
    public List<Person> findFamilyTreePersons(FamilyTree familyTree, Range range, List<Sort> sorts) {
        return repositorySevice.findFamilyTreePersons(familyTree, range, sorts);
    }
}

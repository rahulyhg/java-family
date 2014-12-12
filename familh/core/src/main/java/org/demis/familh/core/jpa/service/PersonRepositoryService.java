package org.demis.familh.core.jpa.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.repository.PersonRepository;
import org.demis.familh.core.jpa.service.converter.SortConverter;
import org.demis.familh.core.service.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="personRepositoryService")
public class PersonRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryService.class);

    @Resource (name = "personRepository")
    private PersonRepository personRepository;

    @Transactional
    public Person create(Person created) {
        return personRepository.save(created);
    }

    @Transactional
    public Person delete(Long id) throws ModelNotFoundException {
        Person deleted = personRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No Person found with id: " + id);
            throw new ModelNotFoundException();
        }

        personRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Person> findPart(int page, int size) {
        return personRepository.findAll(new PageRequest(page, size)).getContent();
    }

    @Transactional(readOnly = true)
    public Person findById(Long id) {
        return personRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Person> findFamilyTreePersons(FamilyTree familyTree, Range range, List<Sort> sorts) {
        Page<Person> entitiesPage =  personRepository.findFamilyTreePersons(familyTree, new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts)));
        if (entitiesPage != null) {
            return entitiesPage.getContent();
        }
        else {
            return null;
        }
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public Person update(Person updated) throws ModelNotFoundException {
        Person person = personRepository.findOne(updated.getId());

        if (person == null) {
            LOGGER.debug("No Person found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            personRepository.save(updated);
        }

        return person;

    }

}

package org.demis.familh.core.jpa.service;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.repository.NameRepository;
import org.demis.familh.core.jpa.repository.PersonRepository;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="personRepositoryService")
public class PersonRepositoryService implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryService.class);

    @Resource (name = "personRepository")
    private PersonRepository personRepository;

    @Resource (name = "nameRepository")
    private NameRepository nameRepository;

    @Transactional
    @Override
    public Person create(Person created) {
        return personRepository.save(created);
    }

    @Override
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
    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> findPart(int page, int size) {
        return personRepository.findAll(new PageRequest(page, size)).getContent();
    }


    @Transactional(readOnly = true)
    @Override
    public Person findById(Long id) {
        return personRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    @Override
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

    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void setNameRepository(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    public List<Person> findFamilyTreePersons(FamilyTree familyTree) {
        return personRepository.findFamilyTreePersons(familyTree);
    }
}

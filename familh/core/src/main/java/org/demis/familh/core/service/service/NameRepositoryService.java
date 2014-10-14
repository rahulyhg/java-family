package org.demis.familh.core.service.service;

import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.repository.NameRepository;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="nameRepositoryService")
public class NameRepositoryService implements NameService {

    private static final Logger logger = LoggerFactory.getLogger(NameRepositoryService.class);

    @Resource (name = "nameRepository")
    private NameRepository nameRepository;

    @Transactional
    @Override
    public Name create(Name created) {
        return nameRepository.save(created);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    @Override
    public Name delete(Long id) throws ModelNotFoundException {
        Name deleted = nameRepository.findOne(id);

        if (deleted == null) {
            logger.debug("No Name found with id: " + id);
            throw new ModelNotFoundException();
        }

        nameRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Name> findAll() {
        return nameRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Name> findPart(int page, int size) {
        return nameRepository.findAll(new PageRequest(page, size)).getContent();
    }

    @Transactional(readOnly = true)
    @Override
    public Name findById(Long id) {
        return nameRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    @Override
    public Name update(Name updated) throws ModelNotFoundException {
        Name name = nameRepository.findOne(updated.getId());

        if (name == null) {
            logger.debug("No Name found with id: " + updated.getId());
            throw new ModelNotFoundException();
        }

        return name;
    }

    @Override
    public List<Name> findPersonNames(Person person) {
        return nameRepository.findPersonNames(person);
    }


    public void setNameRepository(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }
}

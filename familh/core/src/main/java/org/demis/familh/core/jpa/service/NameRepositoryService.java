package org.demis.familh.core.jpa.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.repository.NameRepository;
import org.demis.familh.core.jpa.service.converter.SortConverter;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="nameRepositoryService")
public class NameRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NameRepositoryService.class);

    @Resource (name = "nameRepository")
    private NameRepository nameRepository;

    @Transactional
    public Name create(Name created) {
        return nameRepository.save(created);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public Name delete(Long id) throws ModelNotFoundException {
        Name deleted = nameRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No Name found with id: " + id);
            throw new ModelNotFoundException();
        }

        nameRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    public List<Name> findAll() {
        return nameRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Name findById(Long id) {
        return nameRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public Name update(Name updated) throws ModelNotFoundException {
        Name name = nameRepository.findOne(updated.getId());

        if (name == null) {
            LOGGER.debug("No Name found with id: " + updated.getId());
            throw new ModelNotFoundException();
        }

        return name;
    }

    @Transactional(readOnly = true)
    public List<Name> findPersonNames(Person person, Range range, List<Sort> sorts) {
        Page<Name> entitiesPage =  nameRepository.findPersonNames(person, new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts)));
        if (entitiesPage != null) {
            return entitiesPage.getContent();
        }
        else {
            return null;
        }
    }


    public void setNameRepository(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }
}

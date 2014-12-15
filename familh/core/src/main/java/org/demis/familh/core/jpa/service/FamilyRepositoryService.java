package org.demis.familh.core.jpa.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Family;
import org.demis.familh.core.jpa.repository.NameRepository;
import org.demis.familh.core.jpa.repository.FamilyRepository;
import org.demis.familh.core.jpa.service.converter.SortConverter;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.FamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="familyRepositoryService")
public class FamilyRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FamilyRepositoryService.class);

    @Resource(name = "familyRepository")
    private FamilyRepository familyRepository;

    @Resource (name = "nameRepository")
    private NameRepository nameRepository;

    @Transactional
    public Family create(Family created) {
        return familyRepository.save(created);
    }

    @Transactional
    public Family delete(Long id) throws ModelNotFoundException {
        Family deleted = familyRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No Family found with id: " + id);
            throw new ModelNotFoundException();
        }

        familyRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<Family> findAll() {
        return familyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Family> findPart(int page, int size) {
        return familyRepository.findAll(new PageRequest(page, size)).getContent();
    }

    @Transactional(readOnly = true)
    public Family findById(Long id) {
        return familyRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public Family update(Family updated) throws ModelNotFoundException {
        Family family = familyRepository.findOne(updated.getId());

        if (family == null) {
            LOGGER.debug("No Family found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            familyRepository.save(updated);
        }

        return family;

    }

    @Transactional(readOnly = true)
    public List<Family> findFamilyTreeFamilies(FamilyTree familyTree, Range range, List<Sort> sorts) {
        Page<Family> entitiesPage =  familyRepository.findFamilyTreeFamilies(familyTree, new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts)));
        if (entitiesPage != null) {
            return entitiesPage.getContent();
        }
        else {
            return null;
        }
    }
}

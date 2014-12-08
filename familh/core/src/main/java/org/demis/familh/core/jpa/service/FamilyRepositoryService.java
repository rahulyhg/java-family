package org.demis.familh.core.jpa.service;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Family;
import org.demis.familh.core.jpa.repository.NameRepository;
import org.demis.familh.core.jpa.repository.FamilyRepository;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.FamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="familyRepositoryService")
public class FamilyRepositoryService implements FamilyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FamilyRepositoryService.class);

    @Resource(name = "familyRepository")
    private FamilyRepository familyRepository;

    @Resource (name = "nameRepository")
    private NameRepository nameRepository;

    @Transactional
    @Override
    public Family create(Family created) {
        return familyRepository.save(created);
    }

    @Override
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
    @Override
    public List<Family> findAll() {
        return familyRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Family> findPart(int page, int size) {
        return familyRepository.findAll(new PageRequest(page, size)).getContent();
    }


    @Transactional(readOnly = true)
    @Override
    public Family findById(Long id) {
        return familyRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    @Override
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

    public void setFamilyRepository(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    public void setNameRepository(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    public List<Family> findFamilyTreeFamilies(FamilyTree familyTree) {
        return familyRepository.findFamilyTreeFamilies(familyTree);
    }
}

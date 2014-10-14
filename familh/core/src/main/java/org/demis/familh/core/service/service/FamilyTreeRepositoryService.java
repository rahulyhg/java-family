package org.demis.familh.core.service.service;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.jpa.repository.FamilyTreeRepository;
import org.demis.familh.core.service.FamilyTreeService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="familyTreeRepositoryService")
public class FamilyTreeRepositoryService implements FamilyTreeService {

    private static final Logger logger = LoggerFactory.getLogger(FamilyTreeRepositoryService.class);

    @Resource(name = "familyTreeRepository")
    private FamilyTreeRepository familyTreeRepository;

    @Transactional
    @Override
    public FamilyTree create(FamilyTree created) {
        return familyTreeRepository.save(created);
    }

    @Override
    public FamilyTree delete(Long id) throws ModelNotFoundException {
        FamilyTree deleted = familyTreeRepository.findOne(id);

        if (deleted == null) {
            logger.debug("No FamilyTree found with id: " + id);
            throw new ModelNotFoundException();
        }

        familyTreeRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    @Override
    public List<FamilyTree> findAll() {
        return familyTreeRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<FamilyTree> findPart(int page, int size) {
        return familyTreeRepository.findAll(new PageRequest(page, size)).getContent();
    }


    @Transactional(readOnly = true)
    @Override
    public FamilyTree findById(Long id) {
        return familyTreeRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    @Override
    public FamilyTree update(FamilyTree updated) throws ModelNotFoundException {
        FamilyTree familyTree = familyTreeRepository.findOne(updated.getId());

        if (familyTree == null) {
            logger.debug("No FamilyTree found with id: " + updated.getId());
            throw new ModelNotFoundException();
        }
        else {
            familyTreeRepository.save(updated);
        }

        return familyTree;

    }

    public void setFamilyTreeRepository(FamilyTreeRepository familyTreeRepository) {
        this.familyTreeRepository = familyTreeRepository;
    }

    @Override
    public List<FamilyTree> findUserFamilyTrees(User user) {
        return familyTreeRepository.findUserFamilyTrees(user);
    }
}




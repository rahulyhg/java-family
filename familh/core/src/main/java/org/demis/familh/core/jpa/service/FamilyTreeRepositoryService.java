package org.demis.familh.core.jpa.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.ResourcesPage;
import org.demis.familh.core.Sort;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.jpa.repository.FamilyTreeRepository;
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

@Service(value ="familyTreeRepositoryService")
public class FamilyTreeRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FamilyTreeRepositoryService.class);

    @Resource(name = "familyTreeRepository")
    private FamilyTreeRepository familyTreeRepository;

    @Transactional
    public FamilyTree create(FamilyTree created) {
        return familyTreeRepository.save(created);
    }

    public FamilyTree delete(Long id) throws ModelNotFoundException {
        FamilyTree deleted = familyTreeRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No FamilyTree found with id: " + id);
            throw new ModelNotFoundException();
        }

        familyTreeRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<FamilyTree> findAll() {
        return familyTreeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<FamilyTree> findPart(Range range, List<Sort> sorts) {
        return familyTreeRepository.findAll(new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts))).getContent();
    }


    @Transactional(readOnly = true)
    public FamilyTree findById(Long id) {
        return familyTreeRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public FamilyTree update(FamilyTree updated) throws ModelNotFoundException {
        FamilyTree familyTree = familyTreeRepository.findOne(updated.getId());

        if (familyTree == null) {
            LOGGER.debug("No FamilyTree found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            familyTreeRepository.save(updated);
        }

        return familyTree;

    }

    public ResourcesPage<FamilyTree> findUserFamilyTrees(User user, Range range, List<Sort> sorts) {
        Page<FamilyTree> entitiesPage = familyTreeRepository.findUserFamilyTrees(user, new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts)));
        if (entitiesPage != null) {
            ResourcesPage<FamilyTree> page = new ResourcesPage<>(entitiesPage.getContent(), entitiesPage.getTotalElements());
            return page;
        }
        else {
            return null;
        }
    }
}




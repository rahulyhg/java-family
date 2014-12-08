package org.demis.familh.core.service.impl;

import org.demis.familh.core.elasticsearch.service.FamilyESService;
import org.demis.familh.core.jpa.entity.Family;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("familyService")
public class FamilyServiceImpl extends GenericServiceImpl<Family> implements FamilyService {

    @Autowired
    @Qualifier("familyRepositoryService")
    private FamilyService repositorySevice;

    @Autowired
    @Qualifier ("familyESService")
    private FamilyESService elasticSearchService;


    @Override
    @Transactional
    public Family create(Family created) {
        Family family = repositorySevice.create(created);
        elasticSearchService.create(family);
        return family;
    }

    @Override
    @Transactional
    public Family delete(Long id) throws ModelNotFoundException {
        Family family = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return family;
    }

    @Override
    @Transactional
    public List<Family> findAll() {
        return repositorySevice.findAll();
    }

    @Override
    @Transactional
    public List<Family> findPart(int page, int size) {
        return repositorySevice.findPart(page, size);
    }

    @Override
    @Transactional
    public Family findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Override
    @Transactional
    public Family update(Family updated) throws ModelNotFoundException {
        Family family = repositorySevice.update(updated);
        elasticSearchService.update(family);
        return family;
    }

    @Override
    public List<Family> findFamilyTreeFamilies(FamilyTree familyTree) {
        return null;
    }
}

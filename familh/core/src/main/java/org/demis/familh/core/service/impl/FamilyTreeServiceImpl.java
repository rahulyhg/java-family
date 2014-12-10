package org.demis.familh.core.service.impl;

import org.demis.familh.core.Sort;
import org.demis.familh.core.elasticsearch.service.FamilyTreeESService;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.FamilyTreeService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("familyTreeService")
public class FamilyTreeServiceImpl implements FamilyTreeService {

    @Autowired
    @Qualifier("familyTreeRepositoryService")
    private FamilyTreeService familyTreeService;

    @Autowired
    @Qualifier ("familyTreeESService")
    private FamilyTreeESService elasticSearchService;


    @Override
    @Transactional
    public FamilyTree create(FamilyTree created) {
        FamilyTree familyTree = familyTreeService.create(created);
        elasticSearchService.create(familyTree);
        return familyTree;
    }

    @Override
    @Transactional
    public FamilyTree delete(Long id) throws ModelNotFoundException {
        FamilyTree familyTree = familyTreeService.delete(id);
        elasticSearchService.delete(id);
        return familyTree;
    }

    @Override
    @Transactional
    public List<FamilyTree> findAll() {
        return familyTreeService.findAll();
    }

    @Override
    @Transactional
    public List<FamilyTree> findPart(int page, int size) {
        return familyTreeService.findPart(page, size);
    }

    @Override
    @Transactional
    public FamilyTree findById(Long id) {
        return familyTreeService.findById(id);
    }

    @Override
    @Transactional
    public FamilyTree update(FamilyTree updated) throws ModelNotFoundException {
        FamilyTree familyTree = familyTreeService.update(updated);
        elasticSearchService.update(familyTree);
        return familyTree;
    }

    @Override
    public List<FamilyTree> findUserFamilyTrees(User user, int page, int size, List<Sort> sorts) {
        return familyTreeService.findUserFamilyTrees(user, page, size, sorts);
    }
}

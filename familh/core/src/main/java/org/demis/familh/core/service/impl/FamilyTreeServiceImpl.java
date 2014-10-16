package org.demis.familh.core.service.impl;

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
    private FamilyTreeService repositorySevice;

    @Autowired
    @Qualifier ("familyTreeESService")
    private FamilyTreeESService elasticSearchService;


    @Override
    @Transactional
    public FamilyTree create(FamilyTree created) {
        FamilyTree familyTree = repositorySevice.create(created);
        elasticSearchService.create(familyTree);
        return familyTree;
    }

    @Override
    @Transactional
    public FamilyTree delete(Long id) throws ModelNotFoundException {
        FamilyTree familyTree = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return familyTree;
    }

    @Override
    @Transactional
    public List<FamilyTree> findAll() {
        return repositorySevice.findAll();
    }

    @Override
    @Transactional
    public List<FamilyTree> findPart(int page, int size) {
        return repositorySevice.findPart(page, size);
    }

    @Override
    @Transactional
    public FamilyTree findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Override
    @Transactional
    public FamilyTree update(FamilyTree updated) throws ModelNotFoundException {
        FamilyTree familyTree = repositorySevice.update(updated);
        elasticSearchService.update(familyTree);
        return familyTree;
    }

    @Override
    public List<FamilyTree> findUserFamilyTrees(User user) {
        return repositorySevice.findUserFamilyTrees(user);
    }
}

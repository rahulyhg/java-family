package org.demis.familh.core.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.ResourcesPage;
import org.demis.familh.core.Sort;
import org.demis.familh.core.elasticsearch.service.FamilyTreeESService;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.jpa.service.FamilyTreeRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("familyTreeService")
public class FamilyTreeService {

    @Autowired
    @Qualifier("familyTreeRepositoryService")
    private FamilyTreeRepositoryService familyTreeRepositoryService;

    @Autowired
    @Qualifier ("familyTreeESService")
    private FamilyTreeESService elasticSearchService;


    @Transactional
    public FamilyTree create(FamilyTree created) {
        FamilyTree familyTree = familyTreeRepositoryService.create(created);
        elasticSearchService.create(familyTree);
        return familyTree;
    }

    @Transactional
    public FamilyTree delete(Long id) throws ModelNotFoundException {
        FamilyTree familyTree = familyTreeRepositoryService.delete(id);
        elasticSearchService.delete(id);
        return familyTree;
    }

    @Transactional
    public List<FamilyTree> findAll() {
        return familyTreeRepositoryService.findAll();
    }

    @Transactional
    public List<FamilyTree> findPart(Range range, List<Sort> sorts) {
        return familyTreeRepositoryService.findPart(range, sorts);
    }

    @Transactional
    public FamilyTree findById(Long id) {
        return familyTreeRepositoryService.findById(id);
    }

    @Transactional
    public FamilyTree update(FamilyTree updated) throws ModelNotFoundException {
        FamilyTree familyTree = familyTreeRepositoryService.update(updated);
        elasticSearchService.update(familyTree);
        return familyTree;
    }

    public ResourcesPage<FamilyTree> findUserFamilyTrees(User user, Range range, List<Sort> sorts) {
        return familyTreeRepositoryService.findUserFamilyTrees(user, range, sorts);
    }
}

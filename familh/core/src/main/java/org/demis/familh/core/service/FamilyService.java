package org.demis.familh.core.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.elasticsearch.service.FamilyESService;
import org.demis.familh.core.jpa.entity.Family;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.service.FamilyRepositoryService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("familyService")
public class FamilyService  {

    @Autowired
    @Qualifier("familyRepositoryService")
    private FamilyRepositoryService repositorySevice;

    @Autowired
    @Qualifier ("familyESService")
    private FamilyESService elasticSearchService;


    @Transactional
    public Family create(Family created) {
        Family family = repositorySevice.create(created);
        elasticSearchService.create(family);
        return family;
    }

    @Transactional
    public Family delete(Long id) throws ModelNotFoundException {
        Family family = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return family;
    }

    @Transactional
    public List<Family> findAll() {
        return repositorySevice.findAll();
    }

    @Transactional
    public Family findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Transactional
    public Family update(Family updated) throws ModelNotFoundException {
        Family family = repositorySevice.update(updated);
        elasticSearchService.update(family);
        return family;
    }

    @Transactional
    public List<Family> findFamilyTreeFamilies(FamilyTree familyTree, Range range, List<Sort> sorts) {
        return repositorySevice.findFamilyTreeFamilies(familyTree, range, sorts);
    }
}

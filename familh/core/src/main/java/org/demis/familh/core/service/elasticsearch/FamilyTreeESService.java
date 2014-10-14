package org.demis.familh.core.service.elasticsearch;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.FamilyTreeService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value ="familyTreeESService")
public class FamilyTreeESService implements FamilyTreeService {

    @Override
    public FamilyTree create(FamilyTree created) {
        return null;
    }

    @Override
    public FamilyTree delete(Long id) throws ModelNotFoundException {
        return null;
    }

    @Override
    public List<FamilyTree> findAll() {
        return null;
    }

    @Override
    public List<FamilyTree> findPart(int page, int size) {
        return null;
    }

    @Override
    public FamilyTree findById(Long id) {
        return null;
    }

    @Override
    public FamilyTree update(FamilyTree updated) throws ModelNotFoundException {
        return null;
    }

    @Override
    public List<FamilyTree> findUserFamilyTrees(User user) {
        return null;
    }
}
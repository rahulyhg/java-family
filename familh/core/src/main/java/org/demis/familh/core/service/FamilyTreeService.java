package org.demis.familh.core.service;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;

import java.util.List;

public interface FamilyTreeService extends GenericService<FamilyTree> {

    List<FamilyTree> findUserFamilyTrees(User user);
}

package org.demis.familh.core.service;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Family;

import java.util.List;

public interface FamilyService extends GenericService<Family> {

    List<Family> findFamilyTreeFamilies(FamilyTree familyTree);
}
package org.demis.familh.core.service;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;

import java.util.List;

public interface PersonService extends GenericService<Person> {

    List<Person> findFamilyTreePersons(FamilyTree familyTree);
}

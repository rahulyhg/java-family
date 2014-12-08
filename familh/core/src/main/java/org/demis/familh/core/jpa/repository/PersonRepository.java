package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.familyTree = ?1")
    List<Person> findFamilyTreePersons(FamilyTree familyTree);

}

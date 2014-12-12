package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository ("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.familyTree = ?1")
    Page<Person> findFamilyTreePersons(FamilyTree familyTree, Pageable pageable);

}

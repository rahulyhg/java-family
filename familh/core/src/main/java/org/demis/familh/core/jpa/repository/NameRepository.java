package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.jpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("nameRepository")
public interface NameRepository extends JpaRepository<Name, Long> {

    @Query ("select n from Name n where n.person = ?1")
    List<Name> findPersonNames(Person person);
}

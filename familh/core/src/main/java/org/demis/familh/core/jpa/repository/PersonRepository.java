package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository ("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long> {


}

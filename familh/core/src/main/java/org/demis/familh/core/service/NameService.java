package org.demis.familh.core.service;

import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.jpa.entity.Person;

import java.util.List;

public interface NameService extends GenericService<Name> {

    List<Name> findPersonNames(Person person);
}

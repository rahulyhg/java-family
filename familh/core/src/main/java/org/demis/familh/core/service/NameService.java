package org.demis.familh.core.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.elasticsearch.service.NameESService;
import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.service.NameRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("nameService")
public class NameService {

    @Autowired
    @Qualifier("nameRepositoryService")
    private NameRepositoryService repositorySevice;

    @Autowired
    @Qualifier ("nameESService")
    private NameESService elasticSearchService;

    @Transactional
    public Name create(Name created) {
        Name name = repositorySevice.create(created);
        elasticSearchService.create(name);
        return name;
    }

    @Transactional
    public Name delete(Long id) throws ModelNotFoundException {
        Name name = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return name;
    }

    @Transactional
    public List<Name> findAll() {
        return repositorySevice.findAll();
    }

    @Transactional
    public List<Name> findPersonNames(Person person, Range range, List<Sort> sorts) {
        return repositorySevice.findPersonNames(person, range, sorts);
    }

    @Transactional
    public Name findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Transactional
    public Name update(Name updated) throws ModelNotFoundException {
        Name name = repositorySevice.update(updated);
        elasticSearchService.update(name);
        return name;
    }
}

package org.demis.familh.core.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.elasticsearch.service.UserESService;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.jpa.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    @Qualifier("userRepositoryService")
    private UserRepositoryService repositorySevice;

    @Autowired
    @Qualifier ("userESService")
    private UserESService elasticSearchService;

    @Transactional
    public User create(User created) {
        User user = repositorySevice.create(created);
        elasticSearchService.create(user);
        return user;
    }

    @Transactional
    public User delete(Long id) throws ModelNotFoundException {
        User user = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return user;
    }

    @Transactional
    public List<User> findAll() {
        return repositorySevice.findAll();
    }

    @Transactional
    public List<User> findPart(Range range, List<Sort> sorts) {
        return repositorySevice.findPart(range, sorts);
    }

    @Transactional
    public User findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Transactional
    public User update(User updated) throws ModelNotFoundException {
        User user = repositorySevice.update(updated);
        elasticSearchService.update(user);
        return user;
    }
}

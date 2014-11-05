package org.demis.familh.core.service.impl;

import org.demis.familh.core.elasticsearch.service.UserESService;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userRepositoryService")
    private UserService repositorySevice;

    @Autowired
    @Qualifier ("userESService")
    private UserService elasticSearchService;


    @Override
    @Transactional
    public User create(User created) {
        User user = repositorySevice.create(created);
        elasticSearchService.create(user);
        return user;
    }

    @Override
    @Transactional
    public User delete(Long id) throws ModelNotFoundException {
        User user = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return user;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return repositorySevice.findAll();
    }

    @Override
    @Transactional
    public List<User> findPart(int page, int size) {
        return repositorySevice.findPart(page, size);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Override
    @Transactional
    public User update(User updated) throws ModelNotFoundException {
        User user = repositorySevice.update(updated);
        elasticSearchService.update(user);
        return user;
    }
}

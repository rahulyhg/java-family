package org.demis.familh.core.service.service;

import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.jpa.repository.UserRepository;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="userRepositoryService")
public class UserRepositoryService implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryService.class);

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Transactional
    @Override
    public User create(User created) {
        return userRepository.save(created);
    }

    @Override
    public User delete(Long id) throws ModelNotFoundException {
        User deleted = userRepository.findOne(id);

        if (deleted == null) {
            logger.debug("No User found with id: " + id);
            throw new ModelNotFoundException();
        }

        userRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findPart(int page, int size) {
        return userRepository.findAll(new PageRequest(page, size)).getContent();
    }


    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    @Override
    public User update(User updated) throws ModelNotFoundException {
        User user = userRepository.findOne(updated.getId());

        if (user == null) {
            logger.debug("No User found with id: " + updated.getId());
            throw new ModelNotFoundException();
        }
        else {
            userRepository.save(updated);
        }

        return user;

    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}



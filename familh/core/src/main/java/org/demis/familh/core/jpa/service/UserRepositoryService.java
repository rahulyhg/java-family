package org.demis.familh.core.jpa.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.jpa.repository.UserRepository;
import org.demis.familh.core.jpa.service.converter.SortConverter;
import org.demis.familh.core.service.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="userRepositoryService")
public class UserRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryService.class);

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Transactional
    public User create(User created) {
        return userRepository.save(created);
    }

    public User delete(Long id) throws ModelNotFoundException {
        User deleted = userRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No User found with id: " + id);
            throw new ModelNotFoundException();
        }

        userRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> findPart(Range range, List<Sort> sorts) {
        return userRepository.findAll(new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts))).getContent();
    }


    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public User update(User updated) throws ModelNotFoundException {
        User user = userRepository.findOne(updated.getId());

        if (user == null) {
            LOGGER.debug("No User found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            userRepository.save(updated);
        }

        return user;

    }
}



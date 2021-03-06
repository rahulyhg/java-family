package org.demis.familh.core.jpa.service;

import org.demis.familh.core.jpa.PersistenceJPAConfig;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.jpa.entity.UserRole;
import org.demis.familh.core.service.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

@ContextHierarchy({
        @ContextConfiguration(classes = PersistenceJPAConfig.class)
})
public class RepositoryUserServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("userRepositoryService")
    public UserRepositoryService repository;

    @Test
    public void create() {

        User user = new User();
        user.setFirstName("james");
        user.setLastName("bond");
        user.setNickName("jiji");
        user.setEmail("james.bond@mi6.uk");
        user.setRole(UserRole.U);

        repository.create(user);

        List<User> users = repository.findAll();
        Assert.assertNotNull(users);
    }

    @AfterMethod
    public void deleteAll() throws ModelNotFoundException {
        List<User> users = repository.findAll();

        for (User user: users) {
            repository.delete(user.getId());
        }
    }

    public static User createUser() {
        User user = new User();
        user.setFirstName("james");
        user.setLastName("bond");
        user.setNickName("jiji");
        user.setEmail("james.bond@mi6.uk");
        user.setRole(UserRole.U);

        return user;
    }
}

package org.demis.familh.core.jpa.service;

import org.demis.familh.core.jpa.PersistenceJPAConfig;
import org.demis.familh.core.jpa.entity.Access;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
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
public class RepositoryFamilyTreeServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("familyTreeRepositoryService")
    public FamilyTreeRepositoryService familyTreeRepository;

    @Autowired
    @Qualifier("userRepositoryService")
    public UserRepositoryService userRepository;


    @Test
    public void create() {

        FamilyTree familyTree = createFamilyTree();
        User user = userRepository.create(RepositoryUserServiceTest.createUser());
        familyTree.setUser(user);
        familyTree.setAccess(Access.U);

        familyTreeRepository.create(familyTree);

        List<FamilyTree> familyTrees = familyTreeRepository.findAll();
        Assert.assertNotNull(familyTrees);
    }

    @AfterMethod
    public void deleteAll() throws ModelNotFoundException {
        List<FamilyTree> familyTrees = familyTreeRepository.findAll();

        for (FamilyTree familyTree: familyTrees) {
            familyTreeRepository.delete(familyTree.getId());
        }

        List<User> users = userRepository.findAll();

        for (User user: users) {
            userRepository.delete(user.getId());
        }
    }

    public static FamilyTree createFamilyTree() {
        FamilyTree familyTree = new FamilyTree();
        familyTree.setAccess(Access.U);

        return familyTree;
    }
}

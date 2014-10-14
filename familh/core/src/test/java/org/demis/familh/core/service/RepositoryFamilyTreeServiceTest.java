package org.demis.familh.core.service;

import org.demis.familh.core.PersistenceJPAConfig;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
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
    public FamilyTreeService familyTreeRepository;

    @Autowired
    @Qualifier("userRepositoryService")
    public UserService userRepository;


    @Test
    public void create() {

        FamilyTree familyTree = createFamilyTree();
        User user = userRepository.create(RepositoryUserServiceTest.createUser());
        familyTree.setUser(user);

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

        return familyTree;
    }
}

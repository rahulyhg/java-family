package org.demis.familh.core.jpa.service;

import org.demis.familh.core.jpa.PersistenceJPAConfig;
import org.demis.familh.core.jpa.entity.Access;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.FamilyTreeService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.PersonService;
import org.demis.familh.core.service.UserService;
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
public class RepositoryPersonServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("familyTreeRepositoryService")
    public FamilyTreeService familyTreeRepository;

    @Autowired
    @Qualifier("userRepositoryService")
    public UserService userRepository;

    @Autowired
    @Qualifier("personRepositoryService")
    public PersonService personRepositoryService;

    @Test
    public void create() {

        Person person = createPerson();
        User user = userRepository.create(RepositoryUserServiceTest.createUser());
        FamilyTree familyTree = RepositoryFamilyTreeServiceTest.createFamilyTree();
        familyTree.setUser(user);
        familyTree = familyTreeRepository.create(familyTree);
        person.setFamilyTree(familyTree);
        person.setUser(user);
        person.setAccess(Access.U);

        personRepositoryService.create(person);

        List<Person> persons = personRepositoryService.findAll();
        Assert.assertNotNull(persons);
    }

    @AfterMethod
    public void deleteAll() throws ModelNotFoundException {
        List<Person> persons = personRepositoryService.findAll();

        for (Person person: persons) {
            personRepositoryService.delete(person.getId());
        }

        List<FamilyTree> familyTrees = familyTreeRepository.findAll();

        for (FamilyTree familyTree: familyTrees) {
            familyTreeRepository.delete(familyTree.getId());
        }

        List<User> users = userRepository.findAll();

        for (User user: users) {
            userRepository.delete(user.getId());
        }
    }

    public static Person createPerson() {
        Person person = new Person();

        return person;
    }

}

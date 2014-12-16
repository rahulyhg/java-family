package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.elasticsearch.ElasticSearchConfig;
import org.demis.familh.core.jpa.PersistenceJPAConfig;
import org.demis.familh.core.jpa.entity.*;
import org.demis.familh.core.service.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;

@ContextHierarchy({
        @ContextConfiguration(classes = ElasticSearchConfig.class),
        @ContextConfiguration(classes = PersistenceJPAConfig.class)
})
public class PersonESServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("personESService")
    private PersonESService service;

    @Test
    public void create() throws IOException {
        // create
        Person person = new Person();
        person.setId(1l);
        person.setIdent("I0001");
        person.setSex(Sex.M);
        // ad names
        Name name1 = new Name();
        name1.setId(1l);
        name1.setFirstName("Stéphane");
        name1.setLastName("Kermabon");
        person.addName(name1);;

        Name name2 = new Name();
        name2.setId(1l);
        name2.setFirstName("Stéphane");
        name2.setLastName("Kermabon Lunion");
        person.addName(name2);;

        // events
        Event event = new Event();
        event.setId(1l);
        event.setIdent("E0001");
        event.setType(EventType.BIRT);
        EventPersonAssociation association = new EventPersonAssociation();
        association.setEvent(event);
        association.setPerson(person);

        person.addEvent(event, EventRoleType.M);

        service.create(person);

        // get
        Person indexedPerson = service.getById(1l);
        Assert.assertNotNull(indexedPerson);
        Assert.assertEquals(person.getId(), indexedPerson.getId());
    }

    @AfterMethod
    public void deleteAll() throws ModelNotFoundException, IOException {
        service.delete(1l);
        // get
        Person indexedPerson = service.getById(1l);
        Assert.assertNull(indexedPerson);
    }

}

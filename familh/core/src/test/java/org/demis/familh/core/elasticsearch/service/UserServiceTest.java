package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.elasticsearch.ElasticSearchConfig;
import org.demis.familh.core.jpa.PersistenceJPAConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@ContextHierarchy({
        @ContextConfiguration(classes = ElasticSearchConfig.class),
        @ContextConfiguration(classes = PersistenceJPAConfig.class)
})
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("userESService")
    private UserESService userESService;

    @Test
    public void create() {
    }

    @AfterMethod
    public void deleteAll() {


    }

}

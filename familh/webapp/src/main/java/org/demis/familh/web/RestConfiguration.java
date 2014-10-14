package org.demis.familh.web;

import org.demis.familh.core.jpa.PersistenceJPAConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.demis.familh.web.converter", "org.demis.familh.web.controller", "org.demis.familh.core"})
@ContextHierarchy({
        @ContextConfiguration(classes = PersistenceJPAConfig.class)
})
public class RestConfiguration  extends WebMvcConfigurerAdapter {

    public static final String REST_BASE_URL = "/rest/api/v1/";

}

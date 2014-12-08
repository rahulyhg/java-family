package org.demis.familh.web;

import org.demis.familh.core.jpa.PersistenceJPAConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
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
@PropertySource(value = {"classpath:familh.properties"})
@Component
public class RestConfiguration  extends WebMvcConfigurerAdapter {

    public static final String REST_BASE_URL = "/rest/api/v1/";

    public static final int DEFAULT_PAGE_SIZE = 20;

    @Autowired
    private Environment environment;

    public int getDefaultPageSize() {
        Integer defaultPageSize = environment.getProperty("rest.controller.default.page.size", Integer.class);
        return defaultPageSize != null ? defaultPageSize.intValue() : DEFAULT_PAGE_SIZE;
    }
}

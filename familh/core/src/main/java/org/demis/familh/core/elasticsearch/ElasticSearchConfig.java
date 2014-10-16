package org.demis.familh.core.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = {"classpath:familh.properties"})
public class ElasticSearchConfig {

    @Autowired
    private Environment environment;

    public String getIndexName() {
        return environment.getProperty("elasticsearch.index.name");
    }
}

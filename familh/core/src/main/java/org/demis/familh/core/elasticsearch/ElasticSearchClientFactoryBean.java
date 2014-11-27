package org.demis.familh.core.elasticsearch;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@PropertySource(value = {"classpath:familh.properties"})
public class ElasticSearchClientFactoryBean extends AbstractFactoryBean<Client> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchClientFactoryBean.class);

    private Client client;

    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Override
    public Class<?> getObjectType() {
        return Client.class;
    }

    @Override
    protected Client createInstance() throws Exception {
        if (client == null) {
            ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder()
                    .put("cluster.name", environment.getProperty("elasticsearch.cluster.name"));

            client = new TransportClient(builder).addTransportAddress(new InetSocketTransportAddress(
                    environment.getProperty("elasticsearch.address.ip"),
                    environment.getProperty("elasticsearch.address.port", Integer.class)));
        }

        return client;
    }

    @PreDestroy
    public void shutdown() {
        LOGGER.info("Shutting down service TransportClient ...");
        if (client != null) {
            client.close();
        }
        LOGGER.info("Elasticsearch TransportClient shutdown.");
    }

    @PostConstruct
    public void createMapping() throws Exception {
        createInstance();
        LOGGER.info("Create Index and Mappings...");

        IndicesExistsResponse res = client.admin().indices().prepareExists(environment.getProperty("elasticsearch.index.name")).execute().actionGet();
        if (!res.isExists()) {
            CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(environment.getProperty("elasticsearch.index.name"));
            createIndexRequestBuilder.execute().actionGet();
        }
        // TODO verify and add mapping



        LOGGER.info("Index and Mappings created");
    }
}

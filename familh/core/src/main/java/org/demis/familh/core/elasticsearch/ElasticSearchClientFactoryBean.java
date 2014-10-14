package org.demis.familh.core.elasticsearch;

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

import javax.annotation.PreDestroy;

@Component
@PropertySource(value = {"classpath:familh.properties"})
public class ElasticSearchClientFactoryBean extends AbstractFactoryBean<Client> {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchClientFactoryBean.class);

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
        logger.info("Shutting down service TransportClient ...");
        if (client != null) {
            client.close();
        }
        logger.info("Elasticsearch TransportClient shutdown.");
    }
}

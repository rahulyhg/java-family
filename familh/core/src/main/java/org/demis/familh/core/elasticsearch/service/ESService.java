package org.demis.familh.core.elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demis.familh.core.elasticsearch.ElasticSearchConfig;
import org.demis.familh.core.elasticsearch.converter.GenericConverter;
import org.demis.familh.core.elasticsearch.dto.DTO;
import org.demis.familh.core.jpa.entity.Model;
import org.demis.familh.core.service.ModelNotFoundException;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ESService<M extends Model, D extends DTO> {

    private static Logger LOGGER = LoggerFactory.getLogger(GenericConverter.class);

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Client client;

    @Autowired
    private ElasticSearchConfig configuration;

    public M create(M created) {
        try {
            client.prepareIndex(configuration.getIndexName(), getMapping(), created.getId().toString())
                    .setSource(mapper.writeValueAsString(getConverter().convertModel(created)))
                    .execute()
                    .actionGet();
        } catch (JsonProcessingException e) {
            LOGGER.error("Error in JSon conversion for model: " + created, e);
        }
        return created;

    }

    public void delete(Long id) throws ModelNotFoundException {
        client.prepareDelete(configuration.getIndexName(), getMapping(), id.toString())
                .execute()
                .actionGet();
    }

    public M update(M updated) throws ModelNotFoundException {
        try {
            client.prepareIndex(configuration.getIndexName(), getMapping(), updated.getId().toString())
                    .setSource(mapper.writeValueAsString(getConverter().convertModel(updated)))
                    .execute()
                    .actionGet();
        } catch (JsonProcessingException e) {
            LOGGER.error("Error in JSon conversion for model: " + updated, e);
        }
        return updated;

    }

    protected abstract GenericConverter<M, D> getConverter();

    protected abstract String getMapping();
}

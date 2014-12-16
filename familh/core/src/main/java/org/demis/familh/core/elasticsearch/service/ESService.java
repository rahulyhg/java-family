package org.demis.familh.core.elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demis.familh.core.dto.DTO;
import org.demis.familh.core.elasticsearch.ElasticSearchConfig;
import org.demis.familh.core.elasticsearch.converter.GenericConverter;
import org.demis.familh.core.jpa.entity.Model;
import org.demis.familh.core.service.ModelNotFoundException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public abstract class ESService<M extends Model, D extends DTO> {

    private static Logger LOGGER = LoggerFactory.getLogger(ESService.class);

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

    public M getById(Long id) throws IOException {
        GetResponse response = client.prepareGet(configuration.getIndexName(), getMapping(), id.toString())
                .execute()
                .actionGet();
        LOGGER.debug("Response as String: " + response.getSourceAsString());
        if (response.getSource() == null) {
            return null;
        }
        else {
            D dto = mapper.readValue(response.getSourceAsString(), getDTOClass());
            return getConverter().convertDTO(dto);
        }
    }

    protected abstract Class<D> getDTOClass();

    protected abstract GenericConverter<M, D> getConverter();

    protected abstract String getMapping();
}

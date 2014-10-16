package org.demis.familh.core.elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demis.familh.core.elasticsearch.ElasticSearchConfig;
import org.demis.familh.core.elasticsearch.converter.UserConverter;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.UserService;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value ="userESService")
public class UserESService implements UserService {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Client client;

    @Autowired
    @Qualifier("userConverterES")
    private UserConverter userConverter;

    @Autowired
    private ElasticSearchConfig configuration;

    @Override
    public User create(User created) {
        try {
            client.prepareIndex(configuration.getIndexName(), getMapping(), created.getId().toString())
                    .setSource(mapper.writeValueAsString(userConverter.convertModel(created)))
                    .execute()
                    .actionGet();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return created;

    }

    @Override
    public User delete(Long id) throws ModelNotFoundException {
        client.prepareDelete(configuration.getIndexName(), getMapping(), id.toString())
                .execute()
                .actionGet();
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findPart(int page, int size) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User update(User updated) throws ModelNotFoundException {
        try {
            client.prepareIndex(configuration.getIndexName(), getMapping(), updated.getId().toString())
                    .setSource(mapper.writeValueAsString(userConverter.convertModel(updated)))
                    .execute()
                    .actionGet();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return updated;

    }

    public String getMapping() {
        return  "user";
    }
}
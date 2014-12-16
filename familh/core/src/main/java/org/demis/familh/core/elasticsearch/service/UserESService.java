package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.elasticsearch.converter.UserConverter;
import org.demis.familh.core.elasticsearch.dto.UserESDTO;
import org.demis.familh.core.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "userESService")
public class UserESService extends ESService<User, UserESDTO> {

    public static final String USER_MAPPING = "user";

    @Autowired
    @Qualifier("userConverterES")
    private UserConverter userConverter;

    @Override
    protected UserConverter getConverter() {
        return userConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }

    @Override
    protected Class<UserESDTO> getDTOClass() {
        return UserESDTO.class;
    }
}
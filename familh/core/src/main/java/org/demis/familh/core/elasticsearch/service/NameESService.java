package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.dto.NameDTO;
import org.demis.familh.core.elasticsearch.converter.NameConverter;
import org.demis.familh.core.jpa.entity.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "nameESService")
public class NameESService extends ESService<Name, NameDTO> {

    public static final String USER_MAPPING = "name";

    @Autowired
    @Qualifier("nameConverterES")
    private NameConverter nameConverter;

    @Override
    protected NameConverter getConverter() {
        return nameConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }

    @Override
    protected Class<NameDTO> getDTOClass() {
        return NameDTO.class;
    }
}
package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.elasticsearch.converter.FamilyConverter;
import org.demis.familh.core.dto.FamilyDTO;
import org.demis.familh.core.jpa.entity.Family;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "familyESService")
public class FamilyESService extends ESService<Family, FamilyDTO> {

    public static final String USER_MAPPING = "family";

    @Autowired
    @Qualifier("familyConverterES")
    private FamilyConverter familyConverter;

    @Override
    protected FamilyConverter getConverter() {
        return familyConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }
}
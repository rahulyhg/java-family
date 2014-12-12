package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.elasticsearch.converter.FamilyTreeConverter;
import org.demis.familh.core.elasticsearch.dto.FamilyTreeESDTO;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value ="familyTreeESService")
public class FamilyTreeESService extends ESService<FamilyTree, FamilyTreeESDTO> {

    public static final String USER_MAPPING = "familyTree";

    @Autowired
    @Qualifier("familyTreeConverterES")
    private FamilyTreeConverter familyTreeConverter;

    @Override
    protected FamilyTreeConverter getConverter() {
        return familyTreeConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }
}
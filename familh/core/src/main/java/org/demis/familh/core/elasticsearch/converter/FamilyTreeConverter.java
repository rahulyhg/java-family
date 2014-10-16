package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.elasticsearch.dto.FamilyTreeDTO;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.springframework.stereotype.Service;

@Service(value = "familyTreeConverterES")
public class FamilyTreeConverter extends GenericConverter<FamilyTree, FamilyTreeDTO> {

    public FamilyTreeConverter() {
        super(FamilyTree.class, FamilyTreeDTO.class);
    }

    @Override
    protected void updateModelFields(FamilyTree familyTree, FamilyTreeDTO familyTreeDTO) {
        familyTree.setId(familyTreeDTO.getId());
        familyTree.setIdent(familyTreeDTO.getIdent());
    }

    @Override
    protected void updateDTOFields(FamilyTreeDTO familytreeDTO, FamilyTree familytree) {
        familytreeDTO.setId(familytree.getId());
        familytreeDTO.setIdent(familytree.getIdent());
    }
}

package org.demis.familh.core.converter;

import org.demis.familh.core.dto.FamilyTreeDTO;
import org.demis.familh.core.jpa.entity.FamilyTree;

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

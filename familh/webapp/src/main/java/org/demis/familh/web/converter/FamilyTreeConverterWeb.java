package org.demis.familh.web.converter;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.web.dto.DTOWeb;
import org.demis.familh.web.dto.FamilyTreeDTOWeb;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service(value = "familyTreeConverterWeb")
public class FamilyTreeConverterWeb extends GenericConverterWeb<FamilyTree, FamilyTreeDTOWeb> {

    public FamilyTreeConverterWeb() {
        super(FamilyTree.class, FamilyTreeDTOWeb.class);
    }

    protected void updateModelFields(FamilyTree familyTree, FamilyTreeDTOWeb familyTreeDTO) {
        familyTree.setName(familyTreeDTO.getName());
        familyTree.setAccess(familyTreeDTO.getAccess());
    }

    protected void updateDTOFields(FamilyTreeDTOWeb familyTreeDTO, FamilyTree familyTree) {
        familyTreeDTO.setName(familyTree.getName());
        familyTreeDTO.setAccess(familyTree.getAccess());
    }

    @Override
    public void generateHref(DTOWeb dto, HttpServletRequest request) {
        super.generateHref(dto, request);
    }
}

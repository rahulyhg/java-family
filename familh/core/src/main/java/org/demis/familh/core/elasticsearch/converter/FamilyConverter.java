package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.dto.FamilyDTO;
import org.demis.familh.core.jpa.entity.Family;
import org.springframework.stereotype.Service;

@Service(value = "familyConverterES")
public class FamilyConverter extends GenericConverter<Family, FamilyDTO> {

    public FamilyConverter() {
        super(Family.class, FamilyDTO.class);
    }

    @Override
    protected void updateModelFields(Family family, FamilyDTO familyDTO) {
        family.setIdent(familyDTO.getIdent());
    }

    @Override
    protected void updateDTOFields(FamilyDTO familyDTO, Family family) {
        familyDTO.setIdent(family.getIdent());
    }
}

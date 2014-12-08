package org.demis.familh.web.converter;

import org.demis.familh.core.jpa.entity.Family;
import org.demis.familh.web.dto.DTOWeb;
import org.demis.familh.web.dto.FamilyDTOWeb;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service(value = "familyConverterWeb")
public class FamilyConverterWeb extends GenericConverterWeb<Family, FamilyDTOWeb> {

    public FamilyConverterWeb() {
        super(Family.class, FamilyDTOWeb.class);
    }

    protected void updateModelFields(Family family, FamilyDTOWeb familyDTO) {
        family.setIdent(familyDTO.getIdent());
    }

    protected void updateDTOFields(FamilyDTOWeb familyDTO, Family family) {
        familyDTO.setIdent(family.getIdent());
    }

    @Override
    public void generateHref(DTOWeb dto, HttpServletRequest request) {
        super.generateHref(dto, request);
    }
}

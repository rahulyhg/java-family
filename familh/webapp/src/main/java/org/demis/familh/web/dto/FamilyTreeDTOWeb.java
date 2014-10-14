package org.demis.familh.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.demis.familh.core.dto.FamilyTreeDTO;

public class FamilyTreeDTOWeb extends FamilyTreeDTO implements DTOWeb {

    private String href;

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "familyTree";
    }

    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }
}
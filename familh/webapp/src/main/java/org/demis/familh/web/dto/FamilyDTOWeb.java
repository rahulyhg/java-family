package org.demis.familh.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.demis.familh.core.elasticsearch.dto.FamilyDTO;

public class FamilyDTOWeb extends FamilyDTO implements DTOWeb {

    private String href;

    public FamilyDTOWeb() {
        super();
    }

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "family";
    }

    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }
}

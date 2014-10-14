package org.demis.familh.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.demis.familh.core.elasticsearch.dto.UserDTO;

public class UserDTOWeb extends UserDTO implements DTOWeb {

    private String href;
    private ResourcesReferenceWeb familyTrees = new ResourcesReferenceWeb("familyTrees");

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "user";
    }

    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

    public ResourcesReferenceWeb getFamilyTrees() {
        return familyTrees;
    }

    public void setFamilyTrees(ResourcesReferenceWeb familyTrees) {
        this.familyTrees = familyTrees;
    }
}

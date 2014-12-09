package org.demis.familh.core.elasticsearch.dto;

import org.demis.familh.core.dto.DTO;
import org.demis.familh.core.jpa.entity.Access;

public class FamilyTreeDTO implements DTO {

    private Long id;
    private String name;
    private Access access;

    public FamilyTreeDTO() {
        // no op
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
}

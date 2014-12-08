package org.demis.familh.core.elasticsearch.dto;

import org.demis.familh.core.jpa.entity.Access;

public class FamilyTreeDTO implements DTO {

    private Long id;
    private String ident;
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

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
}

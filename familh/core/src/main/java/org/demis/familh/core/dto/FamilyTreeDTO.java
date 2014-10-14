package org.demis.familh.core.dto;

public class FamilyTreeDTO implements DTO {

    private Long id;
    private String ident;

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
}

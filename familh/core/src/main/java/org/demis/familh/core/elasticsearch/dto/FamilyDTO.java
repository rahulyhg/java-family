package org.demis.familh.core.elasticsearch.dto;

public class FamilyDTO implements DTO {

    private Long id;
    private String ident;

    public FamilyDTO() {
        // no op
    }

    public Long getId() {
        return id;
    }

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

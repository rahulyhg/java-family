package org.demis.familh.core.elasticsearch.dto;

import org.demis.familh.core.jpa.entity.Sex;

public class PersonDTO implements DTO {

    private Long id;
    private String ident;
    private Sex sex;

    public PersonDTO() {
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}

package org.demis.familh.core.dto;

import org.demis.familh.core.jpa.entity.Access;
import org.demis.familh.core.jpa.entity.Sex;

public class PersonDTO implements DTO {
    private Long id;
    private String ident;
    private Sex sex;
    private Access access;

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

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
}

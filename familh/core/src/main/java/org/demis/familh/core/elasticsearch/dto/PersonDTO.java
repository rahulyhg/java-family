package org.demis.familh.core.elasticsearch.dto;

import org.demis.familh.core.jpa.entity.Access;
import org.demis.familh.core.jpa.entity.Sex;

import java.util.List;

public class PersonDTO implements DTO {

    private Long id;
    private String ident;
    private Sex sex;
    private Access access;
    private List<NameDTO> names;

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

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public List<NameDTO> getNames() {
        return names;
    }

    public void setNames(List<NameDTO> names) {
        this.names = names;
    }
}

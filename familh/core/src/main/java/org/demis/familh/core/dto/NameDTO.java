package org.demis.familh.core.dto;

import org.demis.familh.core.jpa.entity.NameType;

public class NameDTO implements DTO {

    private Long id;
    private String firstName;
    private String lastName;
    private NameType type;

    public NameDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public NameType getType() {
        return type;
    }

    public void setType(NameType type) {
        this.type = type;
    }
}

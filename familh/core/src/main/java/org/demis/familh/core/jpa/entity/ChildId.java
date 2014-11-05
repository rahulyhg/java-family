package org.demis.familh.core.jpa.entity;

import java.io.Serializable;

public class ChildId implements Serializable {

    private Long familyId;

    private Long personId;

    public ChildId(Long familyId, Long personId) {
        this.familyId = familyId;
        this.personId = personId;
    }


    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChildId that = (ChildId) o;

        if (!familyId.equals(that.familyId)) return false;
        if (!personId.equals(that.personId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = familyId.hashCode();
        result = 31 * result + personId.hashCode();
        return result;
    }
}

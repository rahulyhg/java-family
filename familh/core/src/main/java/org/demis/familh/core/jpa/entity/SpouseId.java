package org.demis.familh.core.jpa.entity;

import java.io.Serializable;

public class SpouseId implements Serializable {

    private Long familyId;

    private Long personId;


    public SpouseId(Long familyId, Long personId) {
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

        SpouseId spouseId = (SpouseId) o;

        if (!familyId.equals(spouseId.familyId)) return false;
        if (!personId.equals(spouseId.personId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = familyId.hashCode();
        result = 31 * result + personId.hashCode();
        return result;
    }
}

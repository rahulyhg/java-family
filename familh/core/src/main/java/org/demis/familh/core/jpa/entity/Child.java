package org.demis.familh.core.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="child")
@IdClass(ChildId.class)
public class Child {

    private Long familyId;
    private Long personId;
    private ChildType childType;
    private Family family;
    private Person person;

    @Id
    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    @Id
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "child_id")
    public ChildType getChildType() {
        return childType;
    }

    public void setChildType(ChildType childType) {
        this.childType = childType;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name="family_id", referencedColumnName="family_id")
    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name="person_id", referencedColumnName="person_id")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

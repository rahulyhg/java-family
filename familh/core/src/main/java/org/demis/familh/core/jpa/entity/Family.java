package org.demis.familh.core.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.List;

@Entity
@Table(name="family")
public class Family extends AbstractModel implements Model {

    static private final Logger logger = LoggerFactory.getLogger(Family.class);

    private Long id;
    private String ident;
    private User user;
    private FamilyTree familyTree;
    private Integer version;
    private List<Spouse> spouses;
    private List<Child> children;


    public Family() {
        // no op
    }

    @Id
    @Column(name="family_sequence")
    @SequenceGenerator(name="family_sequence", sequenceName="family_sequence")
    @GeneratedValue(generator="family_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="ident")
    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @JoinColumn(name = "user_id", nullable=true)
    @ManyToOne()
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JoinColumn(name = "family_tree_id", nullable=true)
    @ManyToOne()
    public FamilyTree getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(FamilyTree familyTree) {
        this.familyTree = familyTree;
    }

    @OneToMany(mappedBy="family")
    public List<Spouse> getSpouses() {
        return spouses;
    }

    public void setSpouses(List<Spouse> spouses) {
        this.spouses = spouses;
    }

    public void addSpouse(Person person, SpouseRole role) {
        Spouse spouse = new Spouse();
        spouse.setFamily(this);
        spouse.setFamilyId(this.id);
        spouse.setPerson(person);
        spouse.setPersonId(person.getId());
        spouse.setSpouseRole(role);

        this.spouses.add(spouse);
        person.getSpouses().add(spouse);
    }

    @OneToMany(mappedBy="family")
    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public void addChild(Person person, ChildType childType) {
        Child child = new Child();
        child.setFamily(this);
        child.setFamilyId(this.id);
        child.setPerson(person);
        child.setPersonId(person.getId());
        child.setChildType(childType);

        this.children.add(child);
        person.getChildren().add(child);
    }
}


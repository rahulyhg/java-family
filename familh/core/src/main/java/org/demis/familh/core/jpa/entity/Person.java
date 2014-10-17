package org.demis.familh.core.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="person")
public class Person extends AbstractModel implements Model {

    private static final Logger LOGGER = LoggerFactory.getLogger(Person.class);

    private Long id;
    private String ident;
    private Sex sex;
    private User user;
    private FamilyTree familyTree;
    private List<Name> names = new ArrayList<>();
    private Integer version;
    private List<EventPersonAssociation> events;

    public Person() {
        // no op
    }

    @Id
    @Column(name="person_id")
    @SequenceGenerator(name="person_sequence", sequenceName="person_sequence")
    @GeneratedValue(generator="person_sequence")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "sex_id")
    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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

    public void addName(Name name) {
        names.add(name);
    }

    public void removeName(Name name) {
        names.remove(name);
    }

    @OneToMany(mappedBy = "person")
    public List<Name> getNames() {
        return names;
    }

    public void setNames(List<Name> names) {
        this.names = names;
    }

    @OneToMany(mappedBy="person")
    public List<EventPersonAssociation> getEvents() {
        return events;
    }

    public void setEvents(List<EventPersonAssociation> events) {
        this.events = events;
    }

    public void addEvent(Event event, EventRoleType role) {
        EventPersonAssociation association = new EventPersonAssociation();
        association.setPerson(this);
        association.setPersonId(this.id);
        association.setEvent(event);
        association.setEventId(event.getId());
        association.setType(role);

        this.events.add(association);
        event.getPersons().add(association);
    }

}

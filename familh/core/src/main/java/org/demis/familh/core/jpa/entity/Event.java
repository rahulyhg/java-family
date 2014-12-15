package org.demis.familh.core.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="event")
public class Event extends AbstractModel implements Model {

    private static final Logger LOGGER = LoggerFactory.getLogger(Event.class);

    private Long id;
    private String ident;
    private EventType type;
    private Person person;
    private User user;
    private Integer version;
    // TODO rename ...
    private List<EventPersonAssociation> persons;

    public Event() {
        // no op
    }

    @Id
    @Column(name="event_id")
    @SequenceGenerator(name="event_sequence", sequenceName="event_sequence")
    @GeneratedValue(generator="event_sequence")
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
    @Column(name = "event_type_id")
    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @JoinColumn(name = "person_id", nullable=true)
    @ManyToOne()
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @JoinColumn(name = "user_id", nullable=true)
    @ManyToOne()
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy="event")
    public List<EventPersonAssociation> getPersons() {
        return persons;
    }

    public void setPersons(List<EventPersonAssociation> persons) {
        this.persons = persons;
    }

    public void addPerson( Person person) {
        if (persons == null) {
            persons = new ArrayList<EventPersonAssociation>();
        }
        EventPersonAssociation association = new EventPersonAssociation();
        association.setPerson(person);
        association.setEvent(this);
        association.setEventId(this.getId());
        persons.add(association);
    }
}

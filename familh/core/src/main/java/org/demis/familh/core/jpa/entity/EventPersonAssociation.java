package org.demis.familh.core.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name="event_involved_person")
@IdClass(EventPersonAssociationId.class)
public class EventPersonAssociation {

    private Long eventId;
    private Long personId;
    private EventRoleType type;
    private Event event;
    private Person person;

    @Id
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Id
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "event_involved_person_role_id")
    public EventRoleType getType() {
        return type;
    }

    public void setType(EventRoleType type) {
        this.type = type;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name="event_id", referencedColumnName="event_id")
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

package org.demis.familh.core.jpa.entity;

import java.io.Serializable;

public class EventPersonAssociationId implements Serializable {

    private Long eventId;

    private Long personId;


    public EventPersonAssociationId(Long eventId, Long personId) {
        this.eventId = eventId;
        this.personId = personId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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

        EventPersonAssociationId that = (EventPersonAssociationId) o;

        if (eventId != that.eventId) return false;
        if (personId != that.personId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (eventId ^ (eventId >>> 32));
        result = 31 * result + (int) (personId ^ (personId >>> 32));
        return result;
    }
}

package org.demis.familh.web.dto;

import org.demis.familh.core.dto.EventDTO;

import javax.persistence.Transient;

public class EventDTOWeb extends EventDTO implements DTOWeb {

    private Long personId;
    private String href;

    public EventDTOWeb() {
        super();
    }

    @Override
    public String getResourceName() {
        return "event";
    }

    @Transient
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public ResourceReferenceDTOWeb getPerson() {
        return new ResourceReferenceDTOWeb("person", personId);
    }

    public String getInvolvedPersons() {
        return "/event/" + getId() + "/persons/";
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }
}

package org.demis.familh.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.demis.familh.core.dto.PersonDTO;

public class PersonDTOWeb extends PersonDTO implements DTOWeb {

    private String href;
    private ResourcesReferenceWeb events = new ResourcesReferenceWeb("events");
    private ResourcesReferenceWeb names = new ResourcesReferenceWeb("names");

    public PersonDTOWeb() {
        super();
    }

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "person";
    }

    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

    public ResourcesReferenceWeb getEvents() {
        return events;
    }

    public ResourcesReferenceWeb getNames() {
        return names;
    }
}

package org.demis.familh.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.demis.familh.core.elasticsearch.dto.NameDTO;

public class NameDTOWeb extends NameDTO implements DTOWeb {

    private Long personId;
    private String href;
    private DTOWeb person;

    public NameDTOWeb() {
        super();
    }

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "name";
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
        person = new ResourceReferenceDTOWeb("person", personId);
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }


    public DTOWeb getPerson() {
        return person;
    }
}

package org.demis.familh.web.dto;

import org.demis.familh.core.elasticsearch.dto.DTO;

public interface DTOWeb extends DTO {

    public String getResourceName();

    public void setHref(String href);
}

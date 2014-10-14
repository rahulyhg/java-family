package org.demis.familh.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResourceReferenceDTOWeb implements DTOWeb {

    private Long id;
    @JsonIgnore
    private String resourceName;
    private String href;

    public ResourceReferenceDTOWeb(String resourceName, Long id) {
        this.resourceName = resourceName;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

}

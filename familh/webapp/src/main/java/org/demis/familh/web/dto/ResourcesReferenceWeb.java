package org.demis.familh.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResourcesReferenceWeb {

    @JsonIgnore
    private String referenceName;
    private String href;

    public ResourcesReferenceWeb(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }
}

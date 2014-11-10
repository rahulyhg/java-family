package org.demis.familh.core.elasticsearch.dto;

public class DocumentDTO implements DTO {

    private Long id;

    public DocumentDTO() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}

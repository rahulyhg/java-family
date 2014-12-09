package org.demis.familh.core.elasticsearch.dto;

import org.demis.familh.core.dto.DTO;

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

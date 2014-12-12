package org.demis.familh.core.dto;

import org.demis.familh.core.dto.DTO;

public class NoteDTO implements DTO {

    private Long id;

    public NoteDTO() {
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

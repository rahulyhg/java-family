package org.demis.familh.core.elasticsearch.dto;

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

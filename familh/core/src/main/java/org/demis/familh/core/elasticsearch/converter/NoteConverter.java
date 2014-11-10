package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.elasticsearch.dto.NoteDTO;
import org.demis.familh.core.jpa.entity.Note;
import org.springframework.stereotype.Service;

@Service(value = "noteConverterES")
public class NoteConverter extends GenericConverter<Note, NoteDTO> {

    public NoteConverter() {
        super(Note.class, NoteDTO.class);
    }

    @Override
    protected void updateModelFields(Note note, NoteDTO noteDTO) {
    }

    @Override
    protected void updateDTOFields(NoteDTO noteDTO, Note note) {
    }
}

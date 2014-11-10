package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.elasticsearch.converter.NoteConverter;
import org.demis.familh.core.elasticsearch.dto.NoteDTO;
import org.demis.familh.core.jpa.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "noteESService")
public class NoteESService extends ESService<Note, NoteDTO> {

    public static final String NOTE_MAPPING = "note";

    @Autowired
    @Qualifier("noteConverterES")
    private NoteConverter noteConverter;

    @Override
    protected NoteConverter getConverter() {
        return noteConverter;
    }

    @Override
    protected String getMapping() {
        return NOTE_MAPPING;
    }

}

package org.demis.familh.web.converter;

import org.demis.familh.core.jpa.entity.Note;
import org.demis.familh.web.dto.DTOWeb;
import org.demis.familh.web.dto.NoteDTOWeb;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service(value = "noteConverterWeb")
public class NoteConverterWeb extends GenericConverterWeb<Note, NoteDTOWeb> {

    public NoteConverterWeb() {
        super(Note.class, NoteDTOWeb.class);
    }

    protected void updateModelFields(Note note, NoteDTOWeb noteDTO) {
    }

    protected void updateDTOFields(NoteDTOWeb noteDTO, Note note) {
    }

    public void generateHref(DTOWeb dto, HttpServletRequest request) {
        super.generateHref(dto, request);
    }

}

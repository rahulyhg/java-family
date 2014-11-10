package org.demis.familh.core.jpa.service;

import org.demis.familh.core.jpa.entity.Note;
import org.demis.familh.core.jpa.repository.NameRepository;
import org.demis.familh.core.jpa.repository.NoteRepository;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="noteRepositoryService")
public class NoteRepositoryService implements NoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteRepositoryService.class);

    @Resource(name = "noteRepository")
    private NoteRepository noteRepository;

    @Transactional
    @Override
    public Note create(Note created) {
        return noteRepository.save(created);
    }

    @Override
    public Note delete(Long id) throws ModelNotFoundException {
        Note deleted = noteRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No Note found with id: " + id);
            throw new ModelNotFoundException();
        }

        noteRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Note> findPart(int page, int size) {
        return noteRepository.findAll(new PageRequest(page, size)).getContent();
    }


    @Transactional(readOnly = true)
    @Override
    public Note findById(Long id) {
        return noteRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    @Override
    public Note update(Note updated) throws ModelNotFoundException {
        Note note = noteRepository.findOne(updated.getId());

        if (note == null) {
            LOGGER.debug("No Note found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            noteRepository.save(updated);
        }

        return note;

    }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
}

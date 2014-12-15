package org.demis.familh.core.jpa.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Note;
import org.demis.familh.core.jpa.repository.NameRepository;
import org.demis.familh.core.jpa.repository.NoteRepository;
import org.demis.familh.core.jpa.service.converter.SortConverter;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="noteRepositoryService")
public class NoteRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteRepositoryService.class);

    @Resource(name = "noteRepository")
    private NoteRepository noteRepository;

    @Transactional
    public Note create(Note created) {
        return noteRepository.save(created);
    }

    @Transactional
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
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Note> findFamilyTreeNotes(FamilyTree familyTree, Range range, List<Sort> sorts) {
        Page<Note> entitiesPage =  noteRepository.findFamilyTreeNotes(familyTree, new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts)));
        if (entitiesPage != null) {
            return entitiesPage.getContent();
        }
        else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Note findById(Long id) {
        return noteRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
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
}

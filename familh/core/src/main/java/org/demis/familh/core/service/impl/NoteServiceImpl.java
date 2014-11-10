package org.demis.familh.core.service.impl;

import org.demis.familh.core.elasticsearch.service.NoteESService;
import org.demis.familh.core.jpa.entity.Note;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("noteService")
public class NoteServiceImpl implements NoteService {

    @Autowired
    @Qualifier("noteRepositoryService")
    private NoteService repositorySevice;

    @Autowired
    @Qualifier ("noteESService")
    private NoteESService elasticSearchService;


    @Override
    @Transactional
    public Note create(Note created) {
        Note note = repositorySevice.create(created);
        elasticSearchService.create(note);
        return note;
    }

    @Override
    @Transactional
    public Note delete(Long id) throws ModelNotFoundException {
        Note note = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return note;
    }

    @Override
    @Transactional
    public List<Note> findAll() {
        return repositorySevice.findAll();
    }

    @Override
    @Transactional
    public List<Note> findPart(int page, int size) {
        return repositorySevice.findPart(page, size);
    }

    @Override
    @Transactional
    public Note findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Override
    @Transactional
    public Note update(Note updated) throws ModelNotFoundException {
        Note note = repositorySevice.update(updated);
        elasticSearchService.update(note);
        return note;
    }

}

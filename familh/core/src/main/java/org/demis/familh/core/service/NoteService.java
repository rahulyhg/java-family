package org.demis.familh.core.service;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.elasticsearch.service.NoteESService;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Note;
import org.demis.familh.core.jpa.service.NoteRepositoryService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("noteService")
public class NoteService {

    @Autowired
    @Qualifier("noteRepositoryService")
    private NoteRepositoryService repositorySevice;

    @Autowired
    @Qualifier ("noteESService")
    private NoteESService elasticSearchService;

    @Transactional
    public Note create(Note created) {
        Note note = repositorySevice.create(created);
        elasticSearchService.create(note);
        return note;
    }

    @Transactional
    public Note delete(Long id) throws ModelNotFoundException {
        Note note = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return note;
    }

    @Transactional
    public List<Note> findAll() {
        return repositorySevice.findAll();
    }

    @Transactional
    public List<Note> findFamilyTreeNotes(FamilyTree familyTree, Range range, List<Sort> sorts) {
        return repositorySevice.findFamilyTreeNotes(familyTree, range, sorts);
    }

    @Transactional
    public Note findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Transactional
    public Note update(Note updated) throws ModelNotFoundException {
        Note note = repositorySevice.update(updated);
        elasticSearchService.update(note);
        return note;
    }

}

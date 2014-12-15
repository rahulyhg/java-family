package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("noteRepository")
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("select n from Note n where n.familyTree = ?1")
    Page<Note> findFamilyTreeNotes(FamilyTree familyTree, Pageable pageable);
}

package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("noteRepository")
public interface NoteRepository extends JpaRepository<Note, Long> {
}

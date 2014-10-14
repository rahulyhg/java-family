package org.demis.familh.gedcom.model;

import java.util.Collection;

public interface NoteContainer {
    public void addNote(Note note);

    public void removeNote(Note note);

    public Collection<Note> getNotes();
}

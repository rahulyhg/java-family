package org.demis.familh.gedcom.model;

import java.util.ArrayList;
import java.util.List;

public class Place {

    private Integer placeId;

    private String placeHierarchy;

    private Map map;

    private List<Note> notes = new ArrayList<>();

    private List<Source> sources = new ArrayList<>();

    public Place() {

    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getPlaceHierarchy() {
        return placeHierarchy;
    }

    public void setPlaceHierarchy(String placeHierarchy) {
        this.placeHierarchy = placeHierarchy;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addSource(Source source) {
        sources.add(source);
    }

    public void removeSource(Source source) {
        sources.remove(source);
    }

    public List<Source> getSources() {
        return sources;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}

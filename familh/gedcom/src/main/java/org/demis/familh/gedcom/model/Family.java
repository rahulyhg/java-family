package org.demis.familh.gedcom.model;

import java.util.ArrayList;
import java.util.List;

public class Family {

    private String ident;

    private List<Source> sources = new ArrayList<>();

    private List<Event> events = new ArrayList<>();

    private List<Individual> parents = new ArrayList<>();

    private List<Individual> children = new ArrayList<>();

    private List<Submitter> submitters = new ArrayList<>();

    public Family() {

    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    // Sources
    public void addSource(Source source) {
        sources.add(source);
    }

    public void removeSource(Source source) {
        sources.remove(source);
    }

    public List<Source> getSources() {
        return sources;
    }

    // Events
    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeSource(Event event) {
        events.remove(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    // Parents
    public void addParent(Individual parent) {
        parents.add(parent);
    }

    public void removeParent(Individual parent) {
        parents.remove(parent);
    }

    public List<Individual> getParents() {
        return parents;
    }

    // Children
    public void addChild(Individual parent) {
        children.add(parent);
    }

    public void removeChild(Individual parent) {
        children.remove(parent);
    }

    public List<Individual> getChildren() {
        return children;
    }

    // Submitters
    public void addSubmitter(Submitter submitter) {
        submitters.add(submitter);
    }

    public void removeSubmitter(Submitter submitter) {
        submitters.remove(submitter);
    }

    public List<Submitter> getAllSubmitters() {
        return submitters;
    }



}

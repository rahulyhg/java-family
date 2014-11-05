package org.demis.familh.gedcom.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FamilyTree implements NoteContainer {

    private HashMap<String, Source> sources = new HashMap<>();

    private FamilyTreeSource source;

    private String gedcomVersion;

    private String charsetVersion;

    private String copyright;

    private String filename;

    private String note;

    private GEDCOMDate change;

    private HashMap<String, Submission> submissions = new HashMap<>();

    private List<Submission> fileSubmissions = new ArrayList<>();

    private HashMap<String, Individual> individuals = new HashMap<>();

    private HashMap<String, Family> families = new HashMap<>();

    private HashMap<String, Note> notes = new HashMap<>();

    public void addSource(Source source) {
        sources.put(source.getIdent(), source);
    }

    public boolean containsSource(Source source) {
        return sources.containsKey(source.getIdent());
    }

    public Collection<Source> getAllSources() {
        return sources.values();
    }

    public Source getSource(String ident) {
        return sources.get(ident);
    }

    public FamilyTreeSource getSource() {
        return source;
    }

    public void setSource(FamilyTreeSource source) {
        this.source = source;
    }

    public String getGedcomVersion() {
        return gedcomVersion;
    }

    public void setGedcomVersion(String gedcomVersion) {
        this.gedcomVersion = gedcomVersion;
    }

    public String getCharsetVersion() {
        return charsetVersion;
    }

    public void setCharsetVersion(String charsetVersion) {
        this.charsetVersion = charsetVersion;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void addSubmission(Submission submission) {
        submissions.put(submission.getIdent(), submission);
    }

    public boolean containsSubmission(Submission submission) {
        return submissions.containsKey(submission.getIdent());
    }

    public Collection<Submission> getAllSubmissions() {
        return submissions.values();
    }

    public Submission getSubmission(String ident) {
        return submissions.get(ident);
    }

    public void addFileSubmission(Submission submission) {
        fileSubmissions.add(submission);
    }

    public List<Submission> getFileSubmissions() {
        return fileSubmissions;
    }

    public void addIndividual(Individual individual) {
        individuals.put(individual.getIdent(), individual);
        individual.setFamilyTree(this);
    }

    public boolean containsIndividual(Individual individual) {
        return individuals.containsKey(individual.getIdent());
    }

    public Collection<Individual> getAllIndividuals() {
        return individuals.values();
    }

    public Individual getIndividual(String ident) {
        return individuals.get(ident);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public GEDCOMDate getChange() {
        return change;
    }

    public void setChange(GEDCOMDate change) {
        this.change = change;
    }

    public void addFamily(Family family) {
        families.put(family.getIdent(), family);
    }

    public boolean containsFamily(Family family) {
        return families.containsKey(family.getIdent());
    }

    public Collection<Family> getAllFamilies() {
        return families.values();
    }

    public Family getFamily(String ident) {
        return families.get(ident);
    }

    public void addNote(Note note) {
        notes.put(note.getIdent(), note);
    }

    public void removeNote(Note note) {
        notes.remove(note.getIdent());
    }

    public boolean containsNote(Note note) {
        return notes.containsKey(note.getIdent());
    }

    public Collection<Note> getNotes() {
        return notes.values();
    }

    public Note getNote(String ident) {
        return notes.get(ident);
    }
}

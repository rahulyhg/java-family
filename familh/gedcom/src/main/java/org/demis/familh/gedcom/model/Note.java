package org.demis.familh.gedcom.model;

public class Note {

    private String ident;

    private String text;

    public Note() {

    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

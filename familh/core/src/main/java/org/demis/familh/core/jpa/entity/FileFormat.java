package org.demis.familh.core.jpa.entity;

public enum FileFormat {
    GEDCOM("Gedcom");

    private String label;

    FileFormat(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }
}

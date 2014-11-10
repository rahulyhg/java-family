package org.demis.familh.core.jpa.entity;

public enum SpouseRole {
    F("Father"), M("Mother");

    private String label;

    SpouseRole(String label) {
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

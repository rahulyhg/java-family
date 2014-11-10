package org.demis.familh.core.jpa.entity;

public enum NameType {
    B("Birth Name"), M("Married Name"), A("Also Know As");

    private String label;

    NameType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}

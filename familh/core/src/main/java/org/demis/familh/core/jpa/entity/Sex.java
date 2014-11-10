package org.demis.familh.core.jpa.entity;

public enum Sex {

    F("Female"), M("Male"), U("Unknown");

    private String label;

    Sex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Sex{" +
                "label='" + label + '\'' +
                '}';
    }
}

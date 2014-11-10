package org.demis.familh.core.jpa.entity;

public enum ChildType {

    N("Natural"), L("Legitimate");

    private String label;

    ChildType(String label) {
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

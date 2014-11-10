package org.demis.familh.core.jpa.entity;

public enum EventType {

    DEAT("Death"), BIRT("Birth"), MARR("Marriage"), OTHE("Other");

    private String label;

    EventType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

}

package org.demis.familh.core.jpa.entity;

public enum EventRoleType {

    O("Other"), P("Principal"), M("Mayor"), W("Witness");

    private String label;

    EventRoleType(String label) {
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

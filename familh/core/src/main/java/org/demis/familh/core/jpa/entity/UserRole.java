package org.demis.familh.core.jpa.entity;

public enum UserRole {

    A("Administrator"), U("User");

    private String label;

    UserRole(String label) {
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


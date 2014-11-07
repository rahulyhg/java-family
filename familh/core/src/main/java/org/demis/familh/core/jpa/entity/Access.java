package org.demis.familh.core.jpa.entity;

public enum Access {

    I("Private"), O("Protected"), U("Public");

    private String label;

    Access(String label) {
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

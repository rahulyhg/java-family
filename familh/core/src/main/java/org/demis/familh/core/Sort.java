package org.demis.familh.core;

public class Sort {

    private String property;

    private boolean ascending = true;

    public Sort(String property, boolean ascending) {
        this.property = property;
        this.ascending = ascending;
    }

    public String getProperty() {
        return property;
    }

    public boolean isAscending() {
        return ascending;
    }
}

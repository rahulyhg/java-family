package org.demis.familh.gedcom.model;

public enum DateMode {
    SIMPLE     (1, "exact"),
    BEFORE     (2, "before"),
    AFTER      (3, "after"),
    BETWEEN    (4, "between"),
    CALCULATED (5, "calculated"),
    ESTIMATED  (6, "estimated"),
    ABOUT      (7, "about");

    private int id;
    private String label;

    DateMode(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static DateMode getDateMode(int id) {
        for (DateMode mode: DateMode.values()) {
            if (mode.getId() == id) {
                return  mode;
            }
        }
        return null;
    }
}

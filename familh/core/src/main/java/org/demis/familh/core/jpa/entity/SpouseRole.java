package org.demis.familh.core.jpa.entity;

public enum SpouseRole {
    FATHER("F", "Father"), MOTHER("M", "Mother");

    private String code;

    private String label;

    SpouseRole(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static SpouseRole getByCode(String code) {
        for (SpouseRole spouseRole: values()) {
            if ((spouseRole.code).equals(code)) {
                return spouseRole;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}

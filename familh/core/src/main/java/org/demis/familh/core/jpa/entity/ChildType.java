package org.demis.familh.core.jpa.entity;

public enum ChildType {

    NATURAL("N", "Natural"), LEGITIMATE("L", "Legitimate");

    private String code;

    private String label;

    ChildType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static ChildType getByCode(String code) {
        for (ChildType childType : values()) {
            if ((childType.code).equals(code)) {
                return childType;
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

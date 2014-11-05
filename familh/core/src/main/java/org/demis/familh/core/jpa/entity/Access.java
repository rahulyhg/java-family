package org.demis.familh.core.jpa.entity;

public enum Access {

    PRIVATE("P", "Private"), PROTECTED("O", "Protected"), PUBLIC("U", "Public");

    private String code;

    private String label;

    Access(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static Access getByCode(String code) {
        for (Access access: values()) {
            if ((access.code).equals(code)) {
                return access;
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

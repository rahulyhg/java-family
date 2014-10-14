package org.demis.familh.core.jpa.entity;

public enum NameType {
    B('B', "Birth Name"), M('M', "Married Name"), A('A', "Also Know As");

    private char code;

    private String label;

    NameType(char code, String label) {
        this.code = code;
        this.label = label;
    }

    public static NameType getByCode(String code) {
        for (NameType nameType: values()) {
            if (("" + nameType.code).equals(code)) {
                return nameType;
            }
        }
        return null;
    }

    public char getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "" + code;
    }
}

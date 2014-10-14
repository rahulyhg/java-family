package org.demis.familh.core.jpa.entity;

public enum Sex {

    F('F', "Female"), M('M', "Male");

    private char code;

    private String label;

    Sex(char code, String label) {
        this.code = code;
        this.label = label;
    }

    public static Sex getByCode(String code) {
        for (Sex sex: values()) {
            if (("" + sex.code).equals(code)) {
                return sex;
            }
        }
        return null;
    }

    public char getCode() {
        return code;
    }
}

package org.demis.familh.core.jpa.entity;

public enum UserRole {

    Administrator("A", "Administrator"), User("U", "User");

    private String code;

    private String label;

    UserRole(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static UserRole getByCode(String code) {
        for (UserRole role: values()) {
            if ((role.code).equals(code)) {
                return role;
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


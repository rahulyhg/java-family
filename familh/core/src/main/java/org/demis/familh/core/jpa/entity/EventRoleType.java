package org.demis.familh.core.jpa.entity;

public enum EventRoleType {

    OTHE("OTHE", "Other"), PRIN("PRIN", "Principal"), MAYO("MAYO", "Mayor"), WITN("WITN", "Witness");

    private String code;

    private String label;

    EventRoleType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static EventRoleType getByCode(String code) {
        for (EventRoleType role: values()) {
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

}

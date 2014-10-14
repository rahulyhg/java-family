package org.demis.familh.core.jpa.entity;

public enum EventType {

    DEAT("DEAT", "Death"), BIRT("BIRT", "Birth"), MARR("MARR", "Marriage"), OTHE("OTHE", "Other");

    private String code;

    private String label;

    EventType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static EventType getByCode(String code) {
        for (EventType eventType: values()) {
            if ((eventType.code).equals(code)) {
                return eventType;
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

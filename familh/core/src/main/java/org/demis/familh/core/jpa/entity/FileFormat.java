package org.demis.familh.core.jpa.entity;

public enum FileFormat {
    GEDCOM("GED", "Gedcom");

    private String code;

    private String label;

    FileFormat(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static FileFormat getByCode(String code) {
        for (FileFormat fileFormat: values()) {
            if ((fileFormat.code).equals(code)) {
                return fileFormat;
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

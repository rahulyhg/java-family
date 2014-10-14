package org.demis.familh.gedcom;

public class GEDCOMTuple {

    private int level;

    private String code;

    private String ref;

    private String info;

    private int lineNumber;

    public GEDCOMTuple() {

    }

    public GEDCOMTuple(int level, String code, String ref, String info) {
        this.level = level;
        this.code = code;
        this.ref = ref;
        this.info = info;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String toString() {
        return "(" + lineNumber + ":" +  level + "," + code + "," + ref + ",'" + info + "')";
    }}

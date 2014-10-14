package org.demis.familh.gedcom.model;

public class GEDCOMDate {

    private Integer year;

    private Integer month;

    private Integer day;

    private Integer hour;

    private Integer minute;

    private Integer second;

    private DateMode mode = DateMode.SIMPLE;

    private int modeCode = DateMode.SIMPLE.getId();

    private GEDCOMDate start;

    private GEDCOMDate end;

    public GEDCOMDate() {

    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public DateMode getMode() {
        return mode;
    }

    public void setMode(DateMode mode) {
        this.mode = mode;
        this.modeCode = mode.getId();
    }

    public int getModeCode() {
        return mode.getId();
    }

    public void setModeCode(int modeCode) {
        this.modeCode = modeCode;
        this.mode = DateMode.getDateMode(modeCode);
    }

    public GEDCOMDate getEnd() {
        return end;
    }

    public void setEnd(GEDCOMDate end) {
        this.end = end;
    }

    public GEDCOMDate getStart() {
        return start;
    }

    public void setStart(GEDCOMDate start) {
        this.start = start;
    }
}

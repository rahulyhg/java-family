package org.demis.familh.gedcom;

public enum GEDCOMMonth {

    JANUARY       (1 , "january"    , "JAN"),
    FEBRUARY      (2 , "february"   , "FEB"),
    MARCH         (3 , "march"      , "MAR"),
    APRIL         (4 , "april"      , "APR"),
    MAY           (5 , "may"        , "MAY"),
    JUNE          (6 , "june"       , "JUN"),
    JULY          (7 , "july"       , "JUL"),
    AUGUST        (8 , "august"     , "AUG"),
    SEPTEMBER     (9 , "september"  , "SEP"),
    OCTOBER       (10, "october"    , "OCT"),
    NOVEMBER      (11, "november"   , "NOV"),
    DECEMBER      (12, "december"   , "DEC");

    private int id;
    private String label;
    private String shortLabel;

    GEDCOMMonth(int id, String label, String shortLabel) {
        this.id = id;
        this.label = label;
        this.shortLabel = shortLabel;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public static GEDCOMMonth getMonth(int id) {
        for (GEDCOMMonth month: GEDCOMMonth.values()) {
            if (month.getId() == id) {
                return  month;
            }
        }
        return null;
    }

    public static GEDCOMMonth getMonth(String shortLabel) {
        for (GEDCOMMonth month: GEDCOMMonth.values()) {
            if (month.getShortLabel().equals(shortLabel)) {
                return  month;
            }
        }
        return null;
    }
}

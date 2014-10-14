package org.demis.familh.gedcom;


import org.demis.familh.gedcom.model.DateMode;
import org.demis.familh.gedcom.model.GEDCOMDate;

public class GEDCOMDateParser {

    public static GEDCOMDate parseDate(String text) {
        GEDCOMDate date = new GEDCOMDate();
        parseDate(date, text);
        return date;
    }

    public static GEDCOMDate parseTime(String text) {
        GEDCOMDate date = new GEDCOMDate();
        parseTime(date, text);
        return date;
    }

    public static void parseTime(GEDCOMDate date, String text) {
        String[] elements = text.split("\\:");
        date.setHour(Integer.parseInt(elements[0]));
        date.setMinute(Integer.parseInt(elements[1]));
        date.setSecond(Integer.parseInt(elements[2]));
    }

    public static void parseDate(GEDCOMDate date, String text) {
        if (text.contains("BEF.")) {
            date.setMode(DateMode.BEFORE);
            parseDateElement(date, text.substring(text.indexOf(" ") + 1));
        }
        else if (text.contains("AFT.")) {
            date.setMode(DateMode.AFTER);
            parseDateElement(date, text.substring(text.indexOf(" ") + 1));
        }
        else if (text.contains("BET.")) {
            date.setMode(DateMode.BETWEEN);
            String subtext = text.substring(text.indexOf(" ") + 1);
            GEDCOMDate start = new GEDCOMDate();
            date.setStart(start);
            parseDateElement(start, subtext.substring(0, subtext.indexOf("-") - 1));
            GEDCOMDate end = new GEDCOMDate();
            date.setEnd(end);
            parseDateElement(end, subtext.substring(subtext.indexOf("-") + 2));
        }
        else if (text.contains("CAL.")) {
            date.setMode(DateMode.CALCULATED);
            parseDateElement(date, text.substring(text.indexOf(" ") + 1));
        }
        else if (text.contains("EST.")) {
            date.setMode(DateMode.ESTIMATED);
            parseDateElement(date, text.substring(text.indexOf(" ") + 1));
        }
        else if (text.contains("ABT.")) {
            date.setMode(DateMode.ABOUT);
            parseDateElement(date, text.substring(text.indexOf(" ") + 1));
        }
        else {
            date.setMode(DateMode.SIMPLE);
            parseDateElement(date, text);
        }
    }

    private static void parseDateElement(GEDCOMDate date, String text) {
        String[] elements = text.split(" ");
        if (elements != null && elements.length == 1) {
            date.setYear(Integer.parseInt(elements[0]));
        }
        else if (elements != null && elements.length == 2) {
            date.setYear(Integer.parseInt(elements[1]));
            try {
                date.setMonth(Integer.parseInt(elements[0]));
            }
            catch (NumberFormatException nfe) {
                date.setMonth(GEDCOMMonth.getMonth(elements[1]).getId());
            }
        }
        else if (elements != null && elements.length == 3) {
            date.setYear(Integer.parseInt(elements[2]));
            try {
                date.setMonth(Integer.parseInt(elements[1]));
            }
            catch (NumberFormatException nfe) {
                date.setMonth(GEDCOMMonth.getMonth(elements[1]).getId());
            }
            date.setDay(Integer.parseInt(elements[0]));
        }
    }
}

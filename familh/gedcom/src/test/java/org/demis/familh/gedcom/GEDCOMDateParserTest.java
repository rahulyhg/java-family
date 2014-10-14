package org.demis.familh.gedcom;

import org.demis.familh.gedcom.model.DateMode;
import org.demis.familh.gedcom.model.GEDCOMDate;
import org.testng.annotations.Test;

public class GEDCOMDateParserTest {
    @Test()
    public void testSimple() {
        String text = "1 2 1970";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getYear() == 1970 : "Expected year 1970 : " + date.getYear() ;
        assert date.getMonth() == 2 : "Expected month 2 : " + date.getMonth() ;
        assert date.getDay() == 1 : "Expected day 1 : " + date.getDay() ;
        assert date.getMode() == DateMode.SIMPLE : "Excepted simple date : " + date.getMode().getLabel();
    }

    @Test()
    public void testSimpleYear() {
        String text = "1970";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getYear() == 1970 : "Expected year 1970 : " + date.getYear() ;
        assert date.getMonth() == null : "Expected null month : " + date.getMonth() ;
        assert date.getDay() == null : "Expected null day : " + date.getDay() ;
        assert date.getMode() == DateMode.SIMPLE : "Excepted simple date : " + date.getMode().getLabel();
    }

    @Test()
    public void testSimpleYearAndMonth() {
        String text = "2 1970";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getYear() == 1970 : "Expected year 1970 : " + date.getYear() ;
        assert date.getMonth() == 2 : "Expected month 2 : " + date.getMonth() ;
        assert date.getDay() == null : "Expected null day : " + date.getDay() ;
        assert date.getMode() == DateMode.SIMPLE : "Excepted simple date : " + date.getMode().getLabel();
    }

    @Test()
    public void testBefore() {
        String text = "BEF. 1970";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getYear() == 1970 : "Expected year 1970 : " + date.getYear() ;
        assert date.getMonth() == null : "Expected null month : " + date.getMonth() ;
        assert date.getDay() == null : "Expected null day : " + date.getDay() ;
        assert date.getMode() == DateMode.BEFORE : "Excepted before date : " + date.getMode().getLabel();
    }

    @Test()
    public void testAfter() {
        String text = "AFT. 1970";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getYear() == 1970 : "Expected year 1970 : " + date.getYear() ;
        assert date.getMonth() == null : "Expected null month : " + date.getMonth() ;
        assert date.getDay() == null : "Expected null day : " + date.getDay() ;
        assert date.getMode() == DateMode.AFTER : "Excepted after date : " + date.getMode().getLabel();
    }

    @Test()
    public void testBetween() {
        String text = "BET. 1970 - 1971";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getStart().getYear() == 1970 : "Expected start year 1970 : " + date.getStart().getYear() ;
        assert date.getEnd().getYear() == 1971 : "Expected end year 1970 : " + date.getEnd().getYear() ;

        assert date.getYear() == null : "Expected null year : " + date.getYear() ;
        assert date.getMonth() == null : "Expected null month : " + date.getMonth() ;
        assert date.getDay() == null : "Expected null day : " + date.getDay() ;

        assert date.getMode() == DateMode.BETWEEN : "Excepted after date : " + date.getMode().getLabel();
    }

    @Test()
    public void testAbout() {
        String text = "ABT. 1970";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getYear() == 1970 : "Expected year 1970 : " + date.getYear() ;
        assert date.getMonth() == null : "Expected null month : " + date.getMonth() ;
        assert date.getDay() == null : "Expected null day : " + date.getDay() ;
        assert date.getMode() == DateMode.ABOUT : "Excepted about date : " + date.getMode().getLabel();
    }

    @Test()
    public void testCalculated() {
        String text = "CAL. 1970";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getYear() == 1970 : "Expected year 1970 : " + date.getYear() ;
        assert date.getMonth() == null : "Expected null month : " + date.getMonth() ;
        assert date.getDay() == null : "Expected null day : " + date.getDay() ;
        assert date.getMode() == DateMode.CALCULATED : "Excepted calculated date : " + date.getMode().getLabel();
    }

    @Test()
    public void testEstimated() {
        String text = "EST. 1970";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getYear() == 1970 : "Expected year 1970 : " + date.getYear() ;
        assert date.getMonth() == null : "Expected null month : " + date.getMonth() ;
        assert date.getDay() == null : "Expected null day : " + date.getDay() ;
        assert date.getMode() == DateMode.ESTIMATED : "Excepted estimated date : " + date.getMode().getLabel();
    }


    @Test()
    public void testTime() {
        String text = "15:30:17";
        GEDCOMDate date = GEDCOMDateParser.parseTime(text) ;
        assert date != null : "Date is null";
        assert date.getHour() == 15 : "Expected hour 15 : " + date.getHour() ;
        assert date.getMinute() == 30 : "Expected minute 30 : " + date.getMinute() ;
        assert date.getSecond() == 17 : "Expected 17 seconde : " + date.getSecond() ;
    }


    @Test()
    public void testMonthAsString() {
        String text = "12 JUL 2011";
        GEDCOMDate date = GEDCOMDateParser.parseDate(text) ;
        assert date != null : "Date is null";
        assert date.getYear() == 2011 : "Expected year 2011 : " + date.getYear() ;
        assert date.getMonth() == 7 : "Expected month 7 : " + date.getMonth() ;
        assert date.getDay() == 12 : "Expected day 12 : " + date.getDay() ;
        assert date.getMode() == DateMode.SIMPLE : "Excepted simple date : " + date.getMode().getLabel();
    }
}

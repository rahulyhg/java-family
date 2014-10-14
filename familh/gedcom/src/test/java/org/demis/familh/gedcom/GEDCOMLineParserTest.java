package org.demis.familh.gedcom;

import org.testng.annotations.Test;

public class GEDCOMLineParserTest {

    @Test()
    public void testEndOfLine() {
        GEDCOMParserImpl parser = new GEDCOMParserImpl();
        GEDCOMTuple tuple = parser.parseLine("0 HEAD\n", 0);

        testLine(tuple, 0, "HEAD", null, null);
    }

    @Test()
    public void testLine() {
        GEDCOMParserImpl parser = new GEDCOMParserImpl();
        // line kind '<level> @<ref>@ <code> <info>'
        testLine(parser.parseLine("0 @N0000@ NOTE Le deux décembre mil neuf cent vingt sept, vingt heures", 0)
                , 0
                , "NOTE"
                , "N0000"
                , "Le deux décembre mil neuf cent vingt sept, vingt heures");
        // line kind '<level> @<ref>@ <code>'
        testLine(parser.parseLine("0 @I0001@ INDI", 0), 0, "INDI", "I0001", null);
        // line kind '<level> <code> @<ref>@'
        testLine(parser.parseLine("1 FAMC @F0001@", 0), 1, "FAMC", "F0001", null);
        // line kind '<level> <code> <info>'
        testLine(parser.parseLine("2 DATE 13 OCT 2010", 0), 2, "DATE", null, "13 OCT 2010");
        testLine(parser.parseLine("1 SEX M", 0), 1, "SEX", null, "M");
        // line kind '<level> <code>'
        testLine(parser.parseLine("0 HEAD", 0), 0, "HEAD", null, null);
    }


    @Test()
    public void testSpecialChar() {
        GEDCOMParserImpl parser = new GEDCOMParserImpl();

        testLine(parser.parseLine("1 FILE /home/demis/Documents/généalogie/sauvegarde gramps/kermabon-16.ged", 0)
                ,1
                ,"FILE"
                ,null
                , "/home/demis/Documents/généalogie/sauvegarde gramps/kermabon-16.ged");
    }

    @Test()
    public void testDoubleSpace() {
        GEDCOMParserImpl parser = new GEDCOMParserImpl();

        testLine(parser.parseLine("1 NAME  Kermabon //", 0)
                ,1
                ,"NAME"
                , null
                , " Kermabon //");
    }

    @Test()
    public void testParenthesis() {
        GEDCOMParserImpl parser = new GEDCOMParserImpl();

        testLine(parser.parseLine("3 PHON (510) 794-6850", 0)
                ,3
                ,"PHON"
                , null
                , "(510) 794-6850");
    }

    private void testLine(GEDCOMTuple tuple, int expectedLevel, String expectedCode, String expectedRef, String expectedInfo) {
        assert expectedLevel == tuple.getLevel() : "Expected level {" + expectedLevel + "} for " + tuple.getLevel() + " (" + tuple + ")";
        if (expectedCode != null) {
            assert expectedCode.equals(tuple.getCode()) : "Expected code {" + expectedCode + "} for " + tuple.getCode() + " (" + tuple + ")";
        }
        else {
            assert tuple.getCode() == null : "Expected {null} code for " + tuple.getCode() + " (" + tuple + ")";
        }
        if (expectedRef != null) {
            assert expectedRef.equals(tuple.getRef()) : "Expected ref {" + expectedRef + "} for " + tuple.getRef() + " (" + tuple + ")";
        }
        else {
            assert tuple.getRef() == null : "Expected {null} ref for " + tuple.getRef() + " (" + tuple + ")";
        }
        if (expectedInfo != null) {
            assert expectedInfo.equals(tuple.getInfo()) : "Expected info {" + expectedInfo + "} for " + tuple.getInfo() + " (" + tuple + ")";
        }
        else {
            assert tuple.getInfo() == null : "Expected {null} info for " + tuple.getInfo() + " (" + tuple + ")";
        }
    }
}

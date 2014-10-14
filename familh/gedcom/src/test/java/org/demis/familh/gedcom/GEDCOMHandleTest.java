package org.demis.familh.gedcom;

import org.demis.familh.gedcom.model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GEDCOMHandleTest {

    @Test()
    public void testHeaderUTF8() {
        GEDCOMParser parser = new GEDCOMParserImpl();
        GEDCOMHandler handler = new GEDCOMHandlerImpl();
        parser.parse("src/test/resources/gedcom/gedcom-header-utf-8.ged", handler);
    }

    @Test()
    public void testHeaderSource() {
        GEDCOMParser parser = new GEDCOMParserImpl();
        GEDCOMHandler handler = new GEDCOMHandlerImpl();
        parser.parse("src/test/resources/gedcom/gedcom-header-source.ged", handler);
        FamilyTree familyTree = handler.getFamilyTree();

        assert familyTree.getSource() != null : "Source expected";
        assert "Gramps".equals(familyTree.getSource().getApprovedSystemId()) : "Expected source approved system id : Gramps and not " + familyTree.getSource().getApprovedSystemId();
        assert "3.2.0-1".equals(familyTree.getSource().getVersion()) : "Expected source version : 3.2.0-1 and not " + familyTree.getSource().getVersion();
        assert "Gramps".equals(familyTree.getSource().getName()) : "Expected source name : Gramps and not " + familyTree.getSource().getName();

        assert familyTree.getSource().getCorporation() != null : "Source corporation expected";
        assert "Gramps Corporation".equals(familyTree.getSource().getCorporation().getName()) : "Expected source name : Gramps Corporation and not " + familyTree.getSource().getCorporation().getName();
        assert "Gramps Corporation".equals(familyTree.getSource().getCorporation().getName()) : "Expected source name : Gramps Corporation and not " + familyTree.getSource().getCorporation().getName();

        assert familyTree.getSource().getData() != null : "Source data expected";
        assert "Source data".equals(familyTree.getSource().getData().getName()) : "Expected copyright name : \"Source data\" and not \"" + familyTree.getSource().getData().getName() + "\"";
        assert "Copyright of data".equals(familyTree.getSource().getData().getCopyright()) : "Expected copyright name : \"Copyright of data\" and not \"" + familyTree.getSource().getData().getCopyright() + "\"";

        assert "/home/demis/Documents/généalogie/sauvegarde gramps/kermabon-16.ged".equals(familyTree.getFilename()) : "Expected copyright name : \"/home/demis/Documents/généalogie/sauvegarde gramps/kermabon-16.ged\" and not \"" + familyTree.getFilename() + "\"";
        assert "Copyright (c) 2011 Not Provided.".equals(familyTree.getCopyright()) : "Expected copyright name : \"Copyright (c) 2011 Not Provided.\" and not \"" + familyTree.getCopyright() + "\"";

    }


    @Test()
    public void testIndividualSimpleName() {
        GEDCOMParser parser = new GEDCOMParserImpl();
        GEDCOMHandler handler = new GEDCOMHandlerImpl();
        parser.parse("src/test/resources/gedcom/gedcom-individual-simplename.ged", handler);
        FamilyTree familyTree = handler.getFamilyTree();

        Assert.assertNotNull(familyTree);
        Assert.assertEquals(familyTree.getAllIndividuals().size(), 1);
        Assert.assertNotNull(familyTree.getIndividual("I0001"));

        /* Individual */
        Individual individual = familyTree.getIndividual("I0001");
        Assert.assertNotNull(individual);
        Assert.assertEquals(individual.getIndividualNames().size(), 1);
        Assert.assertEquals(individual.getPersonalName(), "Stéphane René Pierre /Kermabon/");
        Assert.assertEquals(individual.getSex(), "M");
        Assert.assertEquals(individual.getOccupation(), "Ingénieur Informatique");

        /* Name */
        IndividualName name = individual.getIndividualNames().iterator().next();
        Assert.assertEquals(name.getPersonalName(), "Stéphane René Pierre /Kermabon/");
        Assert.assertEquals(name.getGivenName(), "Stéphane René Pierre");
        Assert.assertEquals(name.getSurname(), "Kermabon");

        /* Birthday */
        Event birthday = individual.getEvents().iterator().next();
        Assert.assertEquals(birthday.getType(), "BIRT");
        Assert.assertNotNull(birthday.getEventDate());
        /* Date */
        GEDCOMDate date = birthday.getEventDate();
        Assert.assertNotNull(date);
        Assert.assertEquals(date.getDay().intValue(), 15);
        Assert.assertEquals(date.getMonth().intValue(), 11);
        Assert.assertEquals(date.getYear().intValue(), 1969);
        /* Place */
        Assert.assertNotNull(birthday.getPlace());
        Assert.assertNotNull(birthday.getPlace().getMap());
        Assert.assertEquals(birthday.getPlace().getMap().getLatitude(), 50.2775);
        Assert.assertEquals(birthday.getPlace().getMap().getLongitute(), 3.9734);
        /* Address */
        Assert.assertNotNull(birthday.getAddress());
        Address address = birthday.getAddress();
        Assert.assertEquals(address.getCity(), "Maubeuge");
        Assert.assertEquals(address.getState(), "Nord-Pas-de-Calais");
        Assert.assertEquals(address.getCountry(), "France");


    }

    @Test()
    public void testIndividualMultipleNames() {
        GEDCOMParser parser = new GEDCOMParserImpl();
        GEDCOMHandler handler = new GEDCOMHandlerImpl();
        parser.parse("src/test/resources/gedcom/gedcom-individual-multiplenames.ged", handler);
        FamilyTree familyTree = handler.getFamilyTree();

        Assert.assertNotNull(familyTree);
        Assert.assertEquals(familyTree.getAllIndividuals().size(), 1);
        Assert.assertNotNull(familyTree.getIndividual("id"));

        Individual individual = familyTree.getIndividual("id");
        Assert.assertNotNull(individual);
        Assert.assertEquals(individual.getIndividualNames().size(), 2);

    }

    @Test()
    public void testFamily() {
        GEDCOMParser parser = new GEDCOMParserImpl();
        GEDCOMHandler handler = new GEDCOMHandlerImpl();
        parser.parse("src/test/resources/gedcom/gedcom-family.ged", handler);
        FamilyTree familyTree = handler.getFamilyTree();

        Assert.assertNotNull(familyTree);
        Assert.assertEquals(familyTree.getAllIndividuals().size(), 9);
        Assert.assertEquals(familyTree.getAllFamilies().size(), 1);

        Family famiy = familyTree.getAllFamilies().iterator().next();

        Assert.assertEquals(famiy.getParents().size(), 2);
        Assert.assertEquals(famiy.getChildren().size(), 7);
    }

    @Test()
    public void testNote() {
        GEDCOMParser parser = new GEDCOMParserImpl();
        GEDCOMHandler handler = new GEDCOMHandlerImpl();
        parser.parse("src/test/resources/gedcom/gedcom-note.ged", handler);
        FamilyTree familyTree = handler.getFamilyTree();

        Assert.assertNotNull(familyTree);
        Assert.assertEquals(familyTree.getNotes().size(), 1);

        Note note = familyTree.getNotes().iterator().next();

        Assert.assertEquals(note.getText(), "Le deux décembre mil neuf cent vingt sept, vingt heures\n" +
                "est né rue Neuve 154 Pierre du sexe masculin de Pierre Marie\n" +
                "Kermabon, né à Hennebont, le vingt trois mars mil neuf cent deux\n" +
                "manoeuvre, et de Marie Louise Le Pimpec, née à Inzinzac le\n" +
                "vingt-huit décembre mil neuf cent, ménagère, son épouse, domiciliés\n" +
                "à Hennebont, rue Neuve 154\n" +
                "Dréssé le trois décembre mil neuf cent vingt sept, dix heures\n" +
                "sur la déclaration du père, qui, lecture faite a signé avec --- Camille\n" +
                "--- Maire d'Hennebont, officier de la légion d'Honneur\n");

    }
}

package org.demis.familh.gedcom;

import org.demis.familh.gedcom.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class GEDCOMHandlerImpl implements GEDCOMHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GEDCOMHandlerImpl.class);

    /** Current level of the gedcom file. This is the level of the previous line. */
    private int currentlevel = -1;

    /** Stack of current bean we create in the hierachy. */
    private Stack objectstack = new Stack();

    /** The object we read. */
    private FamilyTree familyTree;

    @Override
    public void handle(GEDCOMTuple tuple) {
        // All line except end of file
        // detect end of object description by a level decrease
        if (!"TRLR".equals(tuple.getCode())) {
            while (tuple.getLevel() <= currentlevel) {
                objectstack.pop();
                currentlevel--;
            }
            currentlevel = tuple.getLevel();
        }

        if ("HEAD".equals(tuple.getCode())) {
            familyTree = new FamilyTree();
            objectstack.push(familyTree);
        } else if ("SUBN".equals(tuple.getCode())) {
            handleSubmission(tuple);
        } else if ("INDI".equals(tuple.getCode()) && tuple.getLevel() == 0) {
            handleIndividual(tuple);
        } else if ("FAM".equals(tuple.getCode()) && tuple.getLevel() == 0) {
            handleFamily(tuple);
        } else if ("NOTE".equals(tuple.getCode())) {
            handleNote(tuple);
        } else if ("SOUR".equals(tuple.getCode())) {
            handleSource(tuple);
        } else if ("VERS".equals(tuple.getCode())) {
            handleVersion(tuple);
        } else if ("NAME".equals(tuple.getCode())) {
            handleName(tuple);
        } else if ("GIVN".equals(tuple.getCode())) {
            handleGivenName(tuple);
        } else if ("SURN".equals(tuple.getCode())) {
            handleSurname(tuple);
        } else if ("CORP".equals(tuple.getCode())) {
            handleCorporate(tuple);
        } else if ("SEX".equals(tuple.getCode())) {
            handleSex(tuple);
        } else if ("OCCU".equals(tuple.getCode())) {
            handleOccupation(tuple);
        } else if ("BIRT".equals(tuple.getCode())) {
            handleEvent(tuple);
        } else if ("ADDR".equals(tuple.getCode())) {
            handleAddress(tuple);
        } else if ("CONT".equals(tuple.getCode())) {
            handleContinue(tuple);
        } else if ("ADR1".equals(tuple.getCode())) {
            handleAddressLine1(tuple);
        } else if ("ADR2".equals(tuple.getCode())) {
            handleAddressLine2(tuple);
        } else if ("CITY".equals(tuple.getCode())) {
            handleCity(tuple);
        } else if ("STAE".equals(tuple.getCode())) {
            handleState(tuple);
        } else if ("POST".equals(tuple.getCode())) {
            handlePostalCode(tuple);
        } else if ("CTRY".equals(tuple.getCode())) {
            handleContry(tuple);
        } else if ("PHON".equals(tuple.getCode())) {
            handlePhoneNumber(tuple);
        } else if ("DATA".equals(tuple.getCode())) {
            handleData(tuple);
        } else if ("DATE".equals(tuple.getCode())) {
            handleDate(tuple);
        } else if ("COPY".equals(tuple.getCode()) || "COPR".equals(tuple.getCode())) {
            handleCopyright(tuple);
        } else if ("TIME".equals(tuple.getCode())) {
            handleTime(tuple);
        } else if ("FILE".equals(tuple.getCode())) {
            handleFilename(tuple);
        } else if ("PLAC".equals(tuple.getCode())) {
            handlePlace(tuple);
        } else if ("MAP".equals(tuple.getCode())) {
            handleMap(tuple);
        } else if ("LATI".equals(tuple.getCode())) {
            handleLatitude(tuple);
        } else if ("LONG".equals(tuple.getCode())) {
            handleLongitude(tuple);
        } else if ("CONC".equals(tuple.getCode())) {
            handleConcat(tuple);
        } else if (  "ANUL".equals(tuple.getCode())
                || "CENS".equals(tuple.getCode())
                || "DIV".equals(tuple.getCode())
                || "DIVF".equals(tuple.getCode())
                || "ENGA".equals(tuple.getCode())
                || "MARR".equals(tuple.getCode())
                || "MARB".equals(tuple.getCode())
                || "MARC".equals(tuple.getCode())
                || "MARL".equals(tuple.getCode())
                || "MARS".equals(tuple.getCode())
                || "EVEN".equals(tuple.getCode())
                ) {
            handleEvent(tuple);
        } else if ("HUSB".equals(tuple.getCode())) {
            handleHusband(tuple);
        } else if ("WIFE".equals(tuple.getCode())) {
            handleWife(tuple);
        } else if ("CHIL".equals(tuple.getCode())) {
            handleChild(tuple);
        } else {
            objectstack.push(tuple);
        }
    }

    private void handleLongitude(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Map) {
            ((Map)(objectstack.peek())).setLongitute(Double.parseDouble(tuple.getInfo()));
        } else {
            LOGGER.warn("No longitude container for " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleLatitude(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Map) {
            ((Map)(objectstack.peek())).setLatitude(Double.parseDouble(tuple.getInfo()));
        } else {
            LOGGER.warn("No latitude container for " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleMap(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Place) {
            Map map = new Map();
            ((Place)(objectstack.peek())).setMap(map);
            objectstack.push(map);
        } else {
            LOGGER.warn("No sex container for " + tuple);
            objectstack.push(tuple);
        }
    }

    private void handleOccupation(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Individual) {
            ((Individual)(objectstack.peek())).setOccupation(tuple.getInfo());
        } else {
            LOGGER.warn("No sex container for " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleSurname(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof IndividualName) {
            ((IndividualName)(objectstack.peek())).setSurname(tuple.getInfo());
        } else {
            LOGGER.warn("No surname container for " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleGivenName(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof IndividualName) {
            ((IndividualName)(objectstack.peek())).setGivenName(tuple.getInfo());
        } else {
            LOGGER.warn("No givenName container for " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleSex(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Individual) {
            ((Individual)(objectstack.peek())).setSex(tuple.getInfo());
        } else {
            LOGGER.warn("No sex container for " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleChild(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Family) {
            Individual individual = familyTree.getIndividual(tuple.getRef());
            ((Family)objectstack.peek()).addChild(individual);
        } else {
            LOGGER.warn("No child container for : " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleWife(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Family) {
            Individual individual = familyTree.getIndividual(tuple.getRef());
            ((Family)objectstack.peek()).addParent(individual);
        } else {
            LOGGER.warn("No wife container for : " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleHusband(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Family) {
            Individual individual = familyTree.getIndividual(tuple.getRef());
            ((Family)objectstack.peek()).addParent(individual);
        } else {
            LOGGER.warn("No husband container for : " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleEvent(GEDCOMTuple tuple) {
        Event event = new Event();
        event.setType(tuple.getCode());
        if (objectstack.peek() instanceof Individual) {
            ((Individual)(objectstack.peek())).addEvent(event);
            objectstack.push(event);
        } else {
            LOGGER.warn("No event container for " + tuple);
            objectstack.push(tuple);
        }
    }

    private void handleConcat(GEDCOMTuple tuple) {
        if (tuple.getInfo() != null) {
            handleConcatOrContinue(tuple.getInfo());
        } else {
            handleConcatOrContinue("");
        }
        objectstack.push(tuple);
    }

    private void handleConcatOrContinue(String text) {
        if (objectstack.peek() instanceof IndividualName) {
            IndividualName name = ((IndividualName)(objectstack.peek()));
            if (name.getPersonalName() != null) {
                name.setPersonalName(name.getPersonalName() + text);
            } else {
                name.setPersonalName(text);
            }
        } else if (objectstack.peek() instanceof Note) {
            Note note = (Note)(objectstack.peek());
            note.setText(note.getText() + text);
        } else if (objectstack.peek() instanceof Address) {
            Address address = ((Address)(objectstack.peek()));
            address.setLine(address.getLine() + text);
        } else if (objectstack.peek() instanceof GEDCOMTuple) {
            GEDCOMTuple previous = (GEDCOMTuple)(objectstack.peek());
            if ("NOTE".equals(previous.getCode())) {
                familyTree.setNote(familyTree.getNote() + text);
            } else {
                LOGGER.warn("No concate, continue container for " + text);
            }
        } else {
            LOGGER.warn("No concate, continue container for " + text);
        }
    }

    private void handlePlace(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof PlaceContainer) {
            Place place = new Place();
            place.setPlaceHierarchy(tuple.getInfo());
            ((PlaceContainer)(objectstack.peek())).setPlace(place);
            objectstack.push(place);
        } else {
            LOGGER.warn("No place container for " + tuple);
            objectstack.push(tuple);
        }
    }

    private void handleFilename(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof FamilyTree) {
            ((FamilyTree)(objectstack.peek())).setFilename(tuple.getInfo());
        } else {
            LOGGER.warn("No filename container for " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleTime(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof GEDCOMDate) {
            GEDCOMDateParser.parseTime((GEDCOMDate) (objectstack.peek()), tuple.getInfo());
        } else {
            LOGGER.warn("No time container for " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleCopyright(GEDCOMTuple tuple) {
        LOGGER.debug("handleCopyright object " + objectstack.peek());
        if (objectstack.peek() instanceof FamilyTreeSourceData) {
            ((FamilyTreeSourceData)(objectstack.peek())).setCopyright(tuple.getInfo());
        } else if (objectstack.peek() instanceof FamilyTree) {
            ((FamilyTree)(objectstack.peek())).setCopyright(tuple.getInfo());
        } else {
            LOGGER.warn("No copyright container for " + tuple + " the previous object is " + objectstack.peek());
        }
        objectstack.push(tuple);
    }

    private void handleDate(GEDCOMTuple tuple) {
        GEDCOMDate date = GEDCOMDateParser.parseDate(tuple.getInfo());
        if (objectstack.peek() instanceof Event) {
            ((Event)(objectstack.peek())).setEventDate(date);
            objectstack.push(date);
        } else if (objectstack.peek() instanceof FamilyTree) {
            ((FamilyTree)(objectstack.peek())).setChange(date);
            objectstack.push(date);
        } else {
            LOGGER.warn("No date container for " + tuple);
            objectstack.push(tuple);
        }
    }

    private void handleData(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof FamilyTreeSource) {
            FamilyTreeSource source = (FamilyTreeSource)(objectstack.peek());
            FamilyTreeSourceData data = source.getData();
            if (data == null) {
                data = new FamilyTreeSourceData();
                source.setData(data);
            }
            data.setName(tuple.getInfo());
            objectstack.push(data);
        } else {
            LOGGER.warn("No data container for " + tuple);
            objectstack.push(tuple);
        }
    }

    private void handlePhoneNumber(GEDCOMTuple tuple) {
        objectstack.push(tuple);
    }

    private void handleContry(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Address) {
            Address address = ((Address)(objectstack.peek()));
            address.setCountry(tuple.getInfo());
        } else {
            LOGGER.warn("AddressModel line 1 not for an AddressModel object");
        }
        objectstack.push(tuple);
    }

    private void handlePostalCode(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Address) {
            Address address = ((Address)(objectstack.peek()));
            address.setPostalCode(tuple.getInfo());
        } else {
            LOGGER.warn("AddressModel line 1 not for an AddressModel object");
        }
        objectstack.push(tuple);

    }

    private void handleState(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Address) {
            Address address = ((Address)(objectstack.peek()));
            address.setState(tuple.getInfo());
        } else {
            LOGGER.warn("AddressModel line 1 not for an AddressModel object");
        }
        objectstack.push(tuple);
    }

    private void handleCity(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Address) {
            Address address = ((Address)(objectstack.peek()));
            address.setCity(tuple.getInfo());
        } else {
            LOGGER.warn("AddressModel line 1 not for an AddressModel object");
        }
        objectstack.push(tuple);
    }

    private void handleAddressLine2(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Address) {
            Address address = ((Address)(objectstack.peek()));
            address.setLine2(tuple.getInfo());
        } else {
            LOGGER.warn("AddressModel line 1 not for an AddressModel object");
        }
        objectstack.push(tuple);
    }

    private void handleAddressLine1(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof Address) {
            Address address = ((Address)(objectstack.peek()));
            address.setLine1(tuple.getInfo());
        } else {
            LOGGER.warn("AddressModel line 1 not for an AddressModel object");
        }
        objectstack.push(tuple);
    }

    private void handleContinue(GEDCOMTuple tuple) {
        if (tuple.getInfo() != null) {
            handleConcatOrContinue("\n" + tuple.getInfo());
        } else {
            handleConcatOrContinue("\n");
        }
        objectstack.push(tuple);
    }

    private void handleAddress(GEDCOMTuple tuple) {
        Address address = new Address();
        address.setLine(tuple.getInfo());
        if (objectstack.peek() instanceof AddressContainer) {
            ((AddressContainer)(objectstack.peek())).setAddress(address);
        } else {
            LOGGER.warn("No address container for " + tuple);
        }
        objectstack.push(address);
    }

    private void handleCorporate(GEDCOMTuple tuple) {
        FamilyTreeSourceCorporation corporation = new FamilyTreeSourceCorporation();
        corporation.setName(tuple.getInfo());
        ((FamilyTreeSource)(objectstack.peek())).setCorporation(corporation);
        objectstack.push(corporation);
    }

    private void handleName(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof FamilyTreeSource) {
            ((FamilyTreeSource)(objectstack.peek())).setName(tuple.getInfo());
            objectstack.push(tuple);
        } else if (objectstack.peek() instanceof Individual) {
            ((Individual)(objectstack.peek())).setPersonalName(tuple.getInfo());
            IndividualName name = new IndividualName();
            ((Individual)(objectstack.peek())).addIndividualName(name);
            name.setPersonalName(tuple.getInfo());
            objectstack.push(name);
        } else {
            objectstack.push(tuple);
        }
    }

    private void handleVersion(GEDCOMTuple tuple) {
        if (objectstack.peek() instanceof FamilyTreeSource) {
            ((FamilyTreeSource)(objectstack.peek())).setVersion(tuple.getInfo());
        } else if (objectstack.peek() instanceof GEDCOMTuple) {
            GEDCOMTuple previous = ((GEDCOMTuple)(objectstack.peek()));
            if ("GEDC".equals(previous.getCode())) {
                familyTree.setGedcomVersion(tuple.getInfo());
            } else if ("CHAR".equals(previous.getCode())) {
                familyTree.setCharsetVersion(tuple.getInfo());
            } else {
                LOGGER.warn("No container for version for " + tuple);
            }
        } else if (objectstack.peek() instanceof FamilyTree) {
            ((FamilyTree)(objectstack.peek())).setGedcomVersion(tuple.getInfo());
        } else {
            LOGGER.warn("No container for version for " + tuple);
        }
        objectstack.push(tuple);
    }

    private void handleSource(GEDCOMTuple tuple) {
        // Primary source declaration
        if (!(!objectstack.isEmpty() && objectstack.peek() instanceof FamilyTree)) {
            Source source = familyTree.getSource(tuple.getRef());
            if (source == null) {
                source = new Source();
                source.setIdent(tuple.getRef());
            }
            if (tuple.getLevel() == 0) {
                familyTree.addSource(source);
            } else if (!objectstack.isEmpty() && objectstack.peek() instanceof SourceContainer) {
                ((SourceContainer)(objectstack.peek())).addSource(source);
            }

            objectstack.push(source);
        } else {
            FamilyTreeSource source = new FamilyTreeSource();
            source.setApprovedSystemId(tuple.getInfo());
            familyTree.setSource(source);
            objectstack.push(source);
        }
    }

    private void handleNote(GEDCOMTuple tuple) {
        if (tuple.getRef() != null) {
            Note note = familyTree.getNote(tuple.getRef());
            if (note == null) {
                note = new Note();
                note.setIdent(tuple.getRef());
                note.setText(tuple.getInfo());
                familyTree.addNote(note);
            }
            if (objectstack.isEmpty()) {
                familyTree.addNote(note);
            } else if (objectstack.peek() instanceof NoteContainer) {
                ((NoteContainer)(objectstack.peek())).addNote(note);
            }
            objectstack.push(note);
        } else if (objectstack.peek() instanceof FamilyTree) {
            ((FamilyTree)(objectstack.peek())).setNote(tuple.getInfo());
            objectstack.push(tuple);
        } else {
            LOGGER.warn("No note container for " + tuple);
            objectstack.push(tuple);
        }
    }

    private void handleFamily(GEDCOMTuple tuple) {
        Family family = familyTree.getFamily(tuple.getRef());
        if (family == null) {
            family = new Family();
            family.setIdent(tuple.getRef());
            familyTree.addFamily(family);
        }
        objectstack.push(family);
    }

    private void handleIndividual(GEDCOMTuple tuple) {
        Individual individual = familyTree.getIndividual(tuple.getRef());
        if (individual == null) {
            individual = new Individual();
            individual.setIdent(tuple.getRef());
            familyTree.addIndividual(individual);
        }
        objectstack.push(individual);
    }

    private void handleSubmission(GEDCOMTuple tuple) {
        if (tuple.getLevel() > 0) {
            if (objectstack.peek() instanceof FamilyTree) {
                FamilyTree tree = (FamilyTree)(objectstack.peek());
                Submission submission = tree.getSubmission(tuple.getRef());
                if (submission == null) {
                    submission = new Submission();
                    submission.setIdent(tuple.getRef());
                }
                tree.addFileSubmission(submission);
                objectstack.push(submission);
            } else {
                LOGGER.warn("No submission container for " + tuple);
                objectstack.push(tuple);
            }
        }
    }

    @Override
    public FamilyTree getFamilyTree() {
        return familyTree;
    }
}

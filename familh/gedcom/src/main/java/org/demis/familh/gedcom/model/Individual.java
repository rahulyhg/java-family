package org.demis.familh.gedcom.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Individual {

    private static final Logger LOGGER = LoggerFactory.getLogger(Individual.class);

    private String ident;

    private String personalName;

    private String sex;

    private FamilyTree familyTree;

    private Integer restrictionNotice;

    private String occupation;

    private List<Source> sources = new ArrayList<>();

    private List<Event> events = new ArrayList<>();

    private Collection<IndividualName> individualNames = new HashSet<>();

    public Individual() {

    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void addSource(Source source) {
        sources.add(source);
    }

    public void removeSource(Source source) {
        sources.remove(source);
    }

    public List<Source> getSources() {
        return sources;
    }

    public FamilyTree getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(FamilyTree familyTree) {
        this.familyTree = familyTree;
    }

    public Integer getRestrictionNotice() {
        return restrictionNotice;
    }

    public void setRestrictionNotice(Integer restrictionNotice) {
        this.restrictionNotice = restrictionNotice;
    }

    public void setIndividualNames(Collection<IndividualName> individuals) {
        this.individualNames = individuals;
    }

    public Collection<IndividualName> getIndividualNames() {
        return individualNames;
    }

    public boolean addIndividualName(IndividualName individualName) {
        boolean addOk = getIndividualNames().add(individualName);

        if (addOk) {
            individualName.setIndividual(this);
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("add returned false");
            }
        }

        return addOk;
    }

    public boolean addIndividualNames(Collection<IndividualName> individuals) {
        boolean addOk = getIndividualNames().addAll(individuals);

        if (addOk) {
            for(IndividualName individualName : individualNames) {
                individualName.setIndividual((Individual)this);
            }
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("add returned false");
            }
        }

        return addOk;
    }

    public boolean containsIndividualName(IndividualName individualName) {
        return getIndividualNames() != null && getIndividualNames().contains(individualName);

    }

    public boolean removeIndividualName(IndividualName individualName) {
        boolean removedOK = getIndividualNames().remove(individualName);

        if (removedOK) {
            individualName.setIndividual(null);
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("remove returned false");
            }
        }

        return removedOK;
    }

    public boolean removeIndividualNames(Collection<IndividualName> individuals) {
        boolean removedOK = getIndividualNames().removeAll(individuals);

        if (removedOK) {
            for(IndividualName individualName : individuals) {
                individualName.setIndividual(null);
            }
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("remove returned false");
            }
        }

        return removedOK;
    }

    public void removeAllIndividualNames() {
        for(IndividualName individualName : getIndividualNames()) {
            individualName.setIndividual(null);
        }
        getIndividualNames().clear();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeSource(Event event) {
        events.remove(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}

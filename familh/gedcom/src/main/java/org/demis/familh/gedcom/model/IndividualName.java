package org.demis.familh.gedcom.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndividualName {

    static private final Logger logger = LoggerFactory.getLogger(IndividualName.class);

    private java.lang.Integer individualNameId = null;

    private java.lang.String personalName = null;

    private java.lang.String namePrefix = null;

    private java.lang.String givenName = null;

    private java.lang.String nickname = null;

    private java.lang.String surnamePrefix = null;

    private java.lang.String surname = null;

    private java.lang.String nameSuffix = null;

    private Individual individual = null;


    public IndividualName() {
        // no op
    }

    public java.lang.Integer getIndividualNameId() {
        return individualNameId ;
    }

    public void setIndividualNameId(java.lang.Integer individualNameId) {
        this.individualNameId = individualNameId ;
    }

    public boolean hasIndividualNameId() {
        return individualNameId != null;
    }

    public java.lang.String getPersonalName() {
        return personalName ;
    }

    public void setPersonalName(java.lang.String personalName) {
        this.personalName = personalName ;
    }

    public java.lang.String getNamePrefix() {
        return namePrefix ;
    }

    public void setNamePrefix(java.lang.String namePrefix) {
        this.namePrefix = namePrefix ;
    }

    public java.lang.String getGivenName() {
        return givenName ;
    }

    public void setGivenName(java.lang.String givenName) {
        this.givenName = givenName ;
    }

    public java.lang.String getNickname() {
        return nickname ;
    }

    public void setNickname(java.lang.String nickname) {
        this.nickname = nickname ;
    }

    public java.lang.String getSurnamePrefix() {
        return surnamePrefix ;
    }

    public void setSurnamePrefix(java.lang.String surnamePrefix) {
        this.surnamePrefix = surnamePrefix ;
    }

    public java.lang.String getSurname() {
        return surname ;
    }

    public void setSurname(java.lang.String surname) {
        this.surname = surname ;
    }

    public java.lang.String getNameSuffix() {
        return nameSuffix ;
    }

    public void setNameSuffix(java.lang.String nameSuffix) {
        this.nameSuffix = nameSuffix ;
    }


    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public  Individual getIndividual() {
        return individual;
    }

    public boolean hasIndividual() {
        return individual != null ;
    }






}




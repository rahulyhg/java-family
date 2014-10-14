package org.demis.familh.gedcom.model.converter;

import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.entity.Sex;
import org.demis.familh.gedcom.model.Individual;

public class IndividualConverter {

    public static Person convert(Individual individual) {
        Person person = new Person();

        person.setIdent(individual.getIdent());
        person.setSex(Sex.getByCode(individual.getSex()));

        return person;
    }

    public static Individual convert(Person person) {
        Individual individual = new Individual();

        individual.setIdent(person.getIdent());
        if (person.getSex() != null)
            individual.setSex("" + person.getSex().getCode());


        return individual;
    }
}

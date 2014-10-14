package org.demis.familh.gedcom.model.converter;

import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.gedcom.model.IndividualName;

public class IndividualNameConverter {

    public static Name convert(IndividualName individualName) {
        Name name = new Name();



        return name;
    }

    public static IndividualName convert(Name name) {
        IndividualName individualName = new IndividualName();



        return individualName;
    }
}

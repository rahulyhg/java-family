package org.demis.familh.gedcom.model.converter;

import org.demis.familh.core.jpa.entity.Sex;

public class SexConverter {

    public static Sex convert(String sex) {
        if (sex == null) {
            return Sex.U;
        }
        else if (sex.equals("M")) {
            return Sex.M;
        }
        else if (sex.equals("F")) {
            return Sex.F;
        }
        else return Sex.U;
    }

    public static String convert(Sex sex) {
        if (sex != null) {
            return sex.name();
        }
        else {
            return null;
        }
    }
}

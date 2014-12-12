package org.demis.familh.web.controller.converter;

import org.demis.familh.core.Range;
import org.demis.familh.core.RequestedRangeUnsatisfiableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RangeConverter {

    private static final Pattern pattern = Pattern.compile("resources[ ]*:[ ]*page[ ]*=[ ]*([0-9]*)[ ]*;[ ]*size[ ]*=[ ]*([0-9]*)");

    public static final Range parse(String value) throws RequestedRangeUnsatisfiableException {
        Matcher matcher = pattern.matcher(value.trim());

        if (matcher.matches()) {
            Range range = new Range();
            range.setPage(Integer.parseInt(matcher.group(1)));
            range.setSize(Integer.parseInt(matcher.group(2)));
            return range;
        } else {
            throw new RequestedRangeUnsatisfiableException();
        }
    }
}

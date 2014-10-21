package org.demis.familh.web.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Range {

    private static final Pattern pattern = Pattern.compile("resources[ ]*:[ ]*page[ ]*=[ ]*([0-9]*)[ ]*;[ ]*size[ ]*=[ ]*([0-9]*)");

    private int page;

    private int size;

    public Range() {

    }

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStart() {
        return page * size;
    }

    public int getEnd() {
        return ((page + 1) * size) - 1;
    }
}

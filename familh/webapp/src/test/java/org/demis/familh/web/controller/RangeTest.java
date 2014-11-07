package org.demis.familh.web.controller;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RangeTest {

    @Test
    public void rangeParsing() throws RequestedRangeUnsatisfiableException {
        Assert.assertNotNull(Range.parse("resources: page=0; size=10"));
        Assert.assertNotNull(Range.parse("resources:page=0; size=10"));
        Assert.assertNotNull(Range.parse("resources:page=0;size=10"));
        Assert.assertNotNull(Range.parse("resources : page = 0 ; size = 10 "));
    }
}

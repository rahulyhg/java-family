package org.demis.familh.web.controller.converter;

import org.demis.familh.core.RequestedRangeUnsatisfiableException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RangeConverterTest {

    @Test
    public void rangeParsing() throws RequestedRangeUnsatisfiableException {
        Assert.assertNotNull(RangeConverter.parse("resources: page=0; size=10"));
        Assert.assertNotNull(RangeConverter.parse("resources:page=0; size=10"));
        Assert.assertNotNull(RangeConverter.parse("resources:page=0;size=10"));
        Assert.assertNotNull(RangeConverter.parse("resources : page = 0 ; size = 10 "));
    }
}

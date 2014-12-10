package org.demis.familh.web.controller;

import org.demis.familh.core.Sort;
import org.demis.familh.web.controller.converter.SortConverter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SortConverterTest {

    @Test
    public void sortAscendantParameterParsing() {
        List<Sort> sorts = SortConverter.parse("attribute");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 1);
        Sort sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute");
        Assert.assertEquals(sort.isAscending(), true);
    }

    @Test
    public void sortDescendantParameterParsing() {
        List<Sort> sorts = SortConverter.parse("-attribute");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 1);
        Sort sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute");
        Assert.assertEquals(sort.isAscending(), false);
    }

    @Test
    public void sortParametersParsing() {
        List<Sort> sorts = SortConverter.parse("-attribute1|attribute2");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 2);

        Sort sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute1");
        Assert.assertEquals(sort.isAscending(), false);

        sort = sorts.get(1);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute2");
        Assert.assertEquals(sort.isAscending(), true);
    }

    @Test
    public void sortEmptyParameterParsing() {
        List<Sort> sorts = SortConverter.parse("-attribute1||attribute2");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 2);

        Sort sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute1");
        Assert.assertEquals(sort.isAscending(), false);

        sort = sorts.get(1);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute2");
        Assert.assertEquals(sort.isAscending(), true);
    }

    @Test
    public void sortEmptyParameterFirstParsing() {
        List<Sort> sorts = SortConverter.parse("|-attribute1||attribute2|");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 2);

        Sort sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute1");
        Assert.assertEquals(sort.isAscending(), false);

        sort = sorts.get(1);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute2");
        Assert.assertEquals(sort.isAscending(), true);
    }

    @Test
    public void sortParametersWithSpaceParsing() {
        List<Sort> sorts = SortConverter.parse(" - attribute1 | attribute2 ");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 2);

        Sort sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute1");
        Assert.assertEquals(sort.isAscending(), false);

        sort = sorts.get(1);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute2");
        Assert.assertEquals(sort.isAscending(), true);
    }
}


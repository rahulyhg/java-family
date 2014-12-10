package org.demis.familh.core.jpa.service.converter;


import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortConverter {

    public static final Sort convert(List<org.demis.familh.core.Sort> sorts) {
        List<Sort.Order> dataSorts = new ArrayList<Sort.Order>();
        for (org.demis.familh.core.Sort sort: sorts) {
            dataSorts.add(new Sort.Order(sort.isAscending()?Sort.Direction.ASC:Sort.Direction.DESC, sort.getProperty()));
        }
        if (dataSorts.isEmpty()) {
            return null;
        }
        else {
            return new Sort(dataSorts);
        }
    }
}

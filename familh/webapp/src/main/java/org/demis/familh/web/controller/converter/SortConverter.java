package org.demis.familh.web.controller.converter;

import org.demis.familh.core.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class SortConverter {

    public static final List<Sort> parse(String value) {
        if (value == null || value.length() == 0) {
            return Collections.emptyList();
        } else {
            List<Sort> sorts = new ArrayList<Sort>();

            StringTokenizer tokenizer = new StringTokenizer(value, "|");
            while (tokenizer.hasMoreElements()) {
                String token = tokenizer.nextToken().trim();
                if (token.length() > 0) {
                    Sort sort;
                    if (token.startsWith("-")) {
                        sort = new Sort(token.substring(1).trim(), false);
                    }
                    else {
                        sort = new Sort(token, true);
                    }
                    sorts.add(sort);
                }
            }
            return sorts;
        }
    }
}

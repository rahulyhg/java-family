package org.demis.familh.core.elasticsearch.mapping;


import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class UserMappingDefinitino {

    public String getMapping() {
        String text = null;
        try {
            text = IOUtils.toString(this.getClass().getResourceAsStream("elasticsearch/mapping/user.json"), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}

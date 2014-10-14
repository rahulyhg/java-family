package org.demis.familh.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/pages/")
public class PageController {

    @RequestMapping ("{page}")
    public String getPage(@PathVariable(value = "page") String page) {
        return page;
    }
}

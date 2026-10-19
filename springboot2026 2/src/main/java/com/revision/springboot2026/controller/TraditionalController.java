package com.revision.springboot2026.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/** Shows the traditional MVC controller style beside @RestController. */
// @Controller marks an MVC controller. By default, a String return value names a view.
@Controller
public class TraditionalController {

    // @ResponseBody changes this method to return text in the HTTP response instead of a view name.
    // @RestController combines @Controller and @ResponseBody for every handler method in a class.
    @ResponseBody
    // Registers this method for GET /api/core-demo/traditional-controller.
    @GetMapping("/api/core-demo/traditional-controller")
    public String explainController() {
        return "@Controller plus @ResponseBody returns response data; @RestController combines both.";
    }
}

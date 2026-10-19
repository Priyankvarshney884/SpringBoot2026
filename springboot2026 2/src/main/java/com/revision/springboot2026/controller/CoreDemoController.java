package com.revision.springboot2026.controller;

import com.revision.springboot2026.core.CoreDemoService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** A small endpoint that makes the Spring Core examples easy to observe. */
// Registers this class as a REST controller; return values are written into the response.
@RestController
// Gives the demo endpoint its common URL prefix.
@RequestMapping("/api/core-demo")
public class CoreDemoController {
    private final CoreDemoService coreDemoService;

    // Spring connects the controller to the service through this constructor.
    public CoreDemoController(CoreDemoService coreDemoService) {
        this.coreDemoService = coreDemoService;
    }

    // Handles GET /api/core-demo and returns the demonstration values as JSON.
    @GetMapping
    public Map<String, Object> showExamples() {
        return coreDemoService.examples();
    }
}

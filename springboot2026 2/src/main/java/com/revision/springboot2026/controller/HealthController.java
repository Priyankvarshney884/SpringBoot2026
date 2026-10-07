package com.revision.springboot2026.controller;

import com.revision.springboot2026.service.HealthService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Receives HTTP requests for health information and turns the result into a web response. */
// Spring registers this class as a web controller. This combines the roles of
// @Controller and @ResponseBody: Spring routes requests here and writes returned
// values into the response (using its JSON converter for this Map).
@RestController
// Adds this URL prefix to the routes declared by methods in this class.
// Without it, each method would need to declare its full URL path.
@RequestMapping("/api/health")
public class HealthController {
    private final HealthService healthService;

    /** Spring calls this constructor and supplies the HealthService object. */
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    // Registers this method for HTTP GET at /api/health. It is a convenient
    // short form of @RequestMapping(method = RequestMethod.GET); Spring does
    // the URL and HTTP-method matching instead of handwritten request checks.
    @GetMapping
    public Map<String, String> health() {
        return Map.of("status", healthService.currentStatus());
    }
}

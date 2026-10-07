package com.revision.springboot2026.controller;

import com.revision.springboot2026.service.HealthService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Receives HTTP requests for health information and turns the result into a web response. */
@RestController
@RequestMapping("/api/health")
public class HealthController {
    private final HealthService healthService;

    /** Spring calls this constructor and supplies the HealthService object. */
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    /** Handles GET /api/health. Spring converts the returned map to JSON. */
    @GetMapping
    public Map<String, String> health() {
        return Map.of("status", healthService.currentStatus());
    }
}

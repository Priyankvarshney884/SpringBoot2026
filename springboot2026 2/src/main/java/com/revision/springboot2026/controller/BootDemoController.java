package com.revision.springboot2026.controller;

import com.revision.bootpoc.BootDemoFeature;
import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Makes the conditional Boot auto-configuration visible through HTTP. */
// Spring registers this class as a REST endpoint; returned values become response data.
@RestController
// Shared prefix for the Boot demonstration route.
@RequestMapping("/api/boot-demo")
public class BootDemoController {
    // This bean is optional because its auto-configuration can be disabled by a property.
    private final ObjectProvider<BootDemoFeature> featureProvider;

    // Spring supplies an ObjectProvider that can look up the conditional bean when needed.
    public BootDemoController(ObjectProvider<BootDemoFeature> featureProvider) {
        this.featureProvider = featureProvider;
    }

    // If the condition matched, show the default bean. If disabled, show that it is absent.
    // Handles GET /api/boot-demo.
    @GetMapping
    public Map<String, String> autoConfigurationStatus() {
        BootDemoFeature feature = featureProvider.getIfAvailable();
        if (feature == null) {
            return Map.of("available", "false", "message", "Auto-configuration was disabled");
        }
        return Map.of("available", "true", "message", feature.message());
    }
}

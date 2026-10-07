package com.revision.springboot2026.service;

import org.springframework.stereotype.Service;

/** Holds the application's health rule; kept separate from HTTP details. */
@Service
public class HealthService {

    public String currentStatus() {
        // For this first example, the application is healthy whenever this method can answer.
        return "UP";
    }
}

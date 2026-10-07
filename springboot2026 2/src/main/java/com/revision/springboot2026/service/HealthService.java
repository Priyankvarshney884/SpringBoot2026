package com.revision.springboot2026.service;

import org.springframework.stereotype.Service;

/** Holds the application's health rule; kept separate from HTTP details. */
// Tells Spring to discover and manage this class as a service bean. It is a
// specialized component marker (like @Component, but communicates this role).
// Spring creates it so application setup does not need to call `new` and wire it.
@Service
public class HealthService {

    public String currentStatus() {
        // For this first example, the application is healthy whenever this method can answer.
        return "UP";
    }
}

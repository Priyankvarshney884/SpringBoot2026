package com.revision.springboot2026.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Lets the console show when a Spring-managed bean is initialized and destroyed. */
public class CoreLifecycleProbe {
    private static final Logger logger = LoggerFactory.getLogger(CoreLifecycleProbe.class);

    public void initialize() {
        logger.info("Core demo lifecycle: bean initialized");
    }

    public void cleanup() {
        logger.info("Core demo lifecycle: bean is shutting down");
    }
}

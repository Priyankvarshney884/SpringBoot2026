package com.revision.springboot2026.core;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** A numbered object used to show that prototype requests create new instances. */
// Component scanning finds this class and registers it with Spring.
@Component
// Prototype means Spring creates a new object each time the container is asked for one.
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeNote {
    private static final AtomicLong nextId = new AtomicLong();

    private final long id = nextId.incrementAndGet();

    public long id() {
        return id;
    }
}

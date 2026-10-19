package com.revision.springboot2026.core;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/** One implementation of the formatting contract. */
// @Component makes this class a Spring bean through component scanning.
// @Primary tells Spring to choose it when only a GreetingFormatter is requested.
@Component
@Primary
public class FriendlyGreetingFormatter implements GreetingFormatter {
    @Override
    public String format(String name) {
        return "Hello, " + name + "!";
    }
}

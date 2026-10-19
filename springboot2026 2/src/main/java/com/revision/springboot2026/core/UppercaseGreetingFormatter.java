package com.revision.springboot2026.core;

import java.util.Locale;
import org.springframework.stereotype.Component;

/** A second implementation so the demo can show @Qualifier choosing a specific bean. */
// This explicit name is the qualifier value used at the injection point.
@Component("uppercaseGreetingFormatter")
public class UppercaseGreetingFormatter implements GreetingFormatter {
    @Override
    public String format(String name) {
        return ("Hello, " + name + "!").toUpperCase(Locale.ROOT);
    }
}

package com.revision.springboot2026.core;

/** A small contract that lets the demo choose between different greeting styles. */
public interface GreetingFormatter {
    String format(String name);
}

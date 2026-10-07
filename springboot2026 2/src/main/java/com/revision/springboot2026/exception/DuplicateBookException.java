package com.revision.springboot2026.exception;

/** Signals a duplicate title/author pair; the advice maps it to HTTP 409. */
public class DuplicateBookException extends RuntimeException {
    public DuplicateBookException() {
        super("A book with this title and author already exists");
    }
}

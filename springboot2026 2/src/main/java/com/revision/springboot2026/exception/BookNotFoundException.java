package com.revision.springboot2026.exception;

/** Signals that a requested book ID does not exist; the advice maps it to HTTP 404. */
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("Book not found: " + id);
    }
}

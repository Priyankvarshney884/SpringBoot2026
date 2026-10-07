package com.revision.springboot2026.practice;

/** A named failure that says exactly what went wrong in the catalog. */
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("No book exists with id " + id);
    }
}

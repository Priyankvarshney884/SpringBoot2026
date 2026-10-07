package com.revision.springboot2026.practice;

/** A record is a concise, immutable-style holder for data coming into an API. */
public record BookRequest(String title, String author, int publicationYear, BookCategory category) {

    // This constructor runs when someone calls new BookRequest(...).
    // Reject bad data at the boundary instead of letting it spread into the program.
    public BookRequest {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author is required");
        }
        if (publicationYear < 0) {
            throw new IllegalArgumentException("publication year cannot be negative");
        }
        if (category == null) {
            throw new IllegalArgumentException("category is required");
        }
    }
}

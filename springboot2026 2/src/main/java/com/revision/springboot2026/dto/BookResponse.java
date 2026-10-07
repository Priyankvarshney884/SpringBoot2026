package com.revision.springboot2026.dto;

/** Data the API sends back to the client. */
public record BookResponse(long id, String title, String author, int publicationYear) {
}

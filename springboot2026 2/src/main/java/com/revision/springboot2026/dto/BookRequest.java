package com.revision.springboot2026.dto;

/** Data the client sends when creating or replacing a book. The server assigns the ID. */
public record BookRequest(String title, String author, int publicationYear) {
}

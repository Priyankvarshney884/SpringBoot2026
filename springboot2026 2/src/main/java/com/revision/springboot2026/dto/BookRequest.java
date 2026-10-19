package com.revision.springboot2026.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Data the client sends when creating or replacing a book. The server assigns the ID. */
public record BookRequest(
        // These constraints are checked when the controller parameter uses @Valid.
        @NotBlank(message = "title must not be blank")
        @Size(max = 200, message = "title must be at most 200 characters")
        String title,

        @NotBlank(message = "author must not be blank")
        @Size(max = 120, message = "author must be at most 120 characters")
        String author,

        @Min(value = 0, message = "publicationYear must be zero or greater")
        int publicationYear) {
}

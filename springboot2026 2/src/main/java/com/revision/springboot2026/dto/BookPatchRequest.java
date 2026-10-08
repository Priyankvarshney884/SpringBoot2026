package com.revision.springboot2026.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Optional fields for a partial update. Null means "leave the existing value unchanged". */
public record BookPatchRequest(
        // Pattern allows null (meaning unchanged), but rejects an explicitly blank title.
        @Pattern(regexp = ".*\\S.*", message = "title must not be blank when provided")
        @Size(max = 200, message = "title must be at most 200 characters")
        String title,

        @Pattern(regexp = ".*\\S.*", message = "author must not be blank when provided")
        @Size(max = 120, message = "author must be at most 120 characters")
        String author,

        // A null year means unchanged; a supplied year must be zero or greater.
        @Min(value = 0, message = "publicationYear must be zero or greater")
        Integer publicationYear) {
}

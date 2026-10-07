package com.revision.springboot2026.domain;

/** A book stored by the application. This is a plain Java class, not a JPA entity yet. */
public class Book {
    private final long id;
    private final String title;
    private final String author;
    private final int publicationYear;

    public Book(long id, String title, String author, int publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
}

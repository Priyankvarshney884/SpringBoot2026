package com.revision.springboot2026.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/** A book stored as a database row. Keep this mutable ordinary class for JPA. */
// @Entity tells the JPA provider to map instances of this class to database rows.
@Entity
// @Table chooses a clear table name instead of relying on a generated name.
@Table(name = "books")
public class Book {
    // @Id marks the primary key; @GeneratedValue lets the database assign new IDs.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 120)
    private String author;

    @Column(name = "publication_year", nullable = false)
    private int publicationYear;

    // Many books may share one publisher; LAZY loads it only when code asks for it.
    // This optional relationship is a persistence POC; the current API DTO exposes no publisher.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    // JPA needs a no-argument constructor to create objects when it reads database rows.
    protected Book() {
    }

    public Book(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public Long getId() {
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

    public Publisher getPublisher() {
        return publisher;
    }

    // Updating a managed entity inside a transaction demonstrates Hibernate dirty checking.
    public void replaceDetails(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }
}

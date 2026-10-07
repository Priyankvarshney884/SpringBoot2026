package com.revision.springboot2026.practice;

import java.util.Objects;

/**
 * A small immutable Java object for practicing classes.
 * It is not a JPA entity. Persistence entities have extra requirements.
 */
public final class Book implements Identifiable, Comparable<Book> {
    private final long id;
    private final String title;
    private final String author;
    private final int publicationYear;
    private final BookCategory category;

    public Book(long id, String title, String author, int publicationYear, BookCategory category) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
        this.id = id;
        this.title = Objects.requireNonNull(title, "title is required");
        this.author = Objects.requireNonNull(author, "author is required");
        if (publicationYear < 0) {
            throw new IllegalArgumentException("publication year cannot be negative");
        }
        this.publicationYear = publicationYear;
        this.category = Objects.requireNonNull(category, "category is required");
    }

    // Public methods are how other classes are allowed to read this object's state.
    @Override
    public long id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String author() {
        return author;
    }

    public int publicationYear() {
        return publicationYear;
    }

    public BookCategory category() {
        return category;
    }

    // Comparable gives Book one natural ordering: oldest year first, then title.
    @Override
    public int compareTo(Book other) {
        int yearComparison = Integer.compare(publicationYear, other.publicationYear);
        if (yearComparison != 0) {
            return yearComparison;
        }
        return title.compareTo(other.title);
    }

    // Two books in this exercise are equal when all their data matches.
    // Keep equals and hashCode consistent when using objects in HashSet/HashMap.
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Book)) {
            return false;
        }
        Book otherBook = (Book) other;
        return id == otherBook.id
                && publicationYear == otherBook.publicationYear
                && title.equals(otherBook.title)
                && author.equals(otherBook.author)
                && category == otherBook.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publicationYear, category);
    }

    @Override
    public String toString() {
        return "Book{id=" + id
                + ", title='" + title + '\''
                + ", author='" + author + '\''
                + ", year=" + publicationYear
                + ", category=" + category + '}';
    }
}

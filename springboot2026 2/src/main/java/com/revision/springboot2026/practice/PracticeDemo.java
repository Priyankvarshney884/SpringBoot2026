package com.revision.springboot2026.practice;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/** Start here: run this main method and read the output one line at a time. */
public class PracticeDemo {
    public static void main(String[] args) {
        // 'new' calls the Book constructor and creates an object.
        Book firstBook = new Book(1, "Effective Java", "Joshua Bloch", 2018, BookCategory.JAVA);
        Book secondBook = new Book(2, "Dune", "Frank Herbert", 1965, BookCategory.FICTION);

        BookCatalog catalog = new BookCatalog();
        catalog.add(firstBook);
        catalog.add(secondBook);

        System.out.println("All books: " + catalog.all());

        // A record gives simple data accessors such as request.title().
        BookRequest request = new BookRequest(
                "Clean Code", "Robert C. Martin", 2008, BookCategory.JAVA);
        System.out.println("New-book request title: " + request.title());

        // The Optional result may be empty if ID 99 is not in the map.
        System.out.println("Book 1: " + catalog.find(1));
        System.out.println("Missing book title: " + catalog.find(99).map(Book::title).orElse("not found"));

        // Lambda: the code after -> is the rule for deciding which books match.
        List<Book> javaBooks = BookCatalog.matching(
                catalog.all(), book -> book.category() == BookCategory.JAVA);
        System.out.println("Java books: " + javaBooks);

        // Method reference: Book::title means "call title() for each Book".
        System.out.println("Sorted by title: " + catalog.sortedByTitle());
        System.out.println("Tasks: " + BookCatalog.processTasks(List.of("validate", "save", "notify")));
        System.out.println("Index by ID: " + BookCatalog.indexById(catalog.all()));
        System.out.println("Natural order: " + firstBook.compareTo(secondBook));

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        System.out.println("Days until next week: " + BookCatalog.daysBetween(today, nextWeek));

        Instant start = Instant.now();
        Instant finish = start.plusSeconds(2);
        System.out.println("Elapsed time: " + BookCatalog.elapsed(start, finish));
    }
}

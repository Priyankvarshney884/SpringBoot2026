package com.revision.springboot2026.practice;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

/** Plain Java catalog. Later, a Spring service could use a repository instead of this Map. */
public class BookCatalog {
    // A Map stores a value under a key. Here the key is a book ID.
    private final Map<Long, Book> booksById = new HashMap<>();

    /** Store one book. HashMap insertion is expected O(1); storage grows O(n). */
    public void add(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book cannot be null");
        }

        // putIfAbsent returns the old value if this ID was already present.
        Book existingBook = booksById.putIfAbsent(book.id(), book);
        if (existingBook != null) {
            throw new IllegalArgumentException("A book already uses id " + book.id());
        }
    }

    /** Optional means there may or may not be a result. Expected O(1) lookup. */
    public Optional<Book> find(long id) {
        Book book = booksById.get(id);
        return Optional.ofNullable(book);
    }

    /** Use this version when a missing book should be an error. */
    public Book require(long id) {
        Optional<Book> result = find(id);
        if (result.isEmpty()) {
            throw new BookNotFoundException(id);
        }
        return result.get();
    }

    /** Return a read-only snapshot, so callers cannot add/remove catalog entries. O(n). */
    public List<Book> all() {
        return List.copyOf(booksById.values());
    }

    /** Sort by title using a Comparator. Sorting takes O(n log n) time. */
    public List<Book> sortedByTitle() {
        List<Book> result = new ArrayList<>(booksById.values());
        result.sort(Comparator.comparing(Book::title));
        return List.copyOf(result);
    }

    /** Generic method: accepts any Identifiable type and returns that same type. */
    public static <T extends Identifiable> Map<Long, T> indexById(List<T> values) {
        Map<Long, T> result = new HashMap<>();
        for (T value : values) {
            result.put(value.id(), value);
        }
        return Map.copyOf(result);
    }

    /** A Set keeps one copy of each equal value. Expected O(n) for n input books. */
    public static List<Book> uniqueBooks(List<Book> books) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (Book book : books) {
            uniqueBooks.add(book);
        }
        return List.copyOf(uniqueBooks);
    }

    /** Queue is first-in, first-out: the first task added is processed first. O(n). */
    public static List<String> processTasks(List<String> tasks) {
        Queue<String> waitingTasks = new ArrayDeque<>();
        waitingTasks.addAll(tasks);

        List<String> completedTasks = new ArrayList<>();
        while (!waitingTasks.isEmpty()) {
            String nextTask = waitingTasks.remove();
            completedTasks.add("processed: " + nextTask);
        }
        return List.copyOf(completedTasks);
    }

    /** Keep books for which the supplied rule returns true. Filtering takes O(n). */
    public static List<Book> matching(List<Book> books, Predicate<Book> rule) {
        List<Book> matches = new ArrayList<>();
        for (Book book : books) {
            if (rule.test(book)) {
                matches.add(book);
            }
        }
        return List.copyOf(matches);
    }

    /** LocalDate is a calendar day, with no time of day or time zone. */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays();
    }

    /** Instant represents a point on the UTC timeline; Duration is time between points. */
    public static Duration elapsed(Instant start, Instant finish) {
        return Duration.between(start, finish);
    }
}

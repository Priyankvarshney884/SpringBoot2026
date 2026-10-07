package com.revision.springboot2026.repository;

import com.revision.springboot2026.domain.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

/** Stores books in memory for this learning step. Data resets when the app restarts. */
// @Repository tells Spring to find and manage this data-access implementation.
@Repository
public class InMemoryBookRepository implements BookRepository {
    private final ConcurrentMap<Long, Book> booksById = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(0);

    @Override
    public List<Book> findAll() {
        // Copy the map values so callers cannot modify the map through this list.
        return new ArrayList<>(booksById.values());
    }

    @Override
    public Optional<Book> findById(long id) {
        // get returns null when the key does not exist; Optional describes that possibility.
        return Optional.ofNullable(booksById.get(id));
    }

    @Override
    public Book save(Book book) {
        long id = book.getId();

        if (id == 0) {
            // A new book has ID 0. The repository assigns the next available ID.
            id = nextId.incrementAndGet();
        } else {
            // Keep the counter ahead if an existing ID is saved during an update.
            nextId.accumulateAndGet(id, Math::max);
        }

        Book bookWithId = new Book(id, book.getTitle(), book.getAuthor(), book.getPublicationYear());
        booksById.put(id, bookWithId);
        return bookWithId;
    }

    @Override
    public boolean deleteById(long id) {
        // Map.remove returns null when no entry with this ID existed.
        return booksById.remove(id) != null;
    }
}

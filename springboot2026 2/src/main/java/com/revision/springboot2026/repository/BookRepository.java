package com.revision.springboot2026.repository;

import com.revision.springboot2026.domain.Book;
import java.util.List;
import java.util.Optional;

/** Describes the book storage operations without saying how storage works. */
public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findById(long id);

    Book save(Book book);

    boolean deleteById(long id);
}

package com.revision.springboot2026.repository;

import com.revision.springboot2026.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data builds the database implementation from this interface at startup. */
// JpaRepository supplies CRUD, paging, sorting, and count operations for Book rows with Long IDs.
public interface BookRepository extends JpaRepository<Book, Long> {
    // Spring parses the method name and creates a case-insensitive SQL query.
    Page<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(
            String title, String author, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    boolean existsByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);

    boolean existsByTitleIgnoreCaseAndAuthorIgnoreCaseAndIdNot(
            String title, String author, Long id);
}

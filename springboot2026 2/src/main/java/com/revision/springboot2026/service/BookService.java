package com.revision.springboot2026.service;

import com.revision.springboot2026.domain.Book;
import com.revision.springboot2026.dto.BookPatchRequest;
import com.revision.springboot2026.dto.BookRequest;
import com.revision.springboot2026.dto.BookResponse;
import com.revision.springboot2026.exception.BookNotFoundException;
import com.revision.springboot2026.exception.DuplicateBookException;
import com.revision.springboot2026.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Applies book rules and maps between API DTOs and JPA entities. */
// Spring creates this service bean and supplies its repository through the constructor.
@Service
// Reads default to read-only; write methods below open normal transactions.
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<BookResponse> findAll(Pageable pageable) {
        // Page.map transforms each row to a DTO and preserves page and sort metadata.
        return bookRepository.findAll(pageable).map(this::toResponse);
    }

    public Page<BookResponse> search(String title, String author, Pageable pageable) {
        String titleFilter = cleanFilter(title);
        String authorFilter = cleanFilter(author);
        Page<Book> results;

        // Use a database query for each combination of optional filters.
        if (!titleFilter.isEmpty() && !authorFilter.isEmpty()) {
            results = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(
                    titleFilter, authorFilter, pageable);
        } else if (!titleFilter.isEmpty()) {
            results = bookRepository.findByTitleContainingIgnoreCase(titleFilter, pageable);
        } else if (!authorFilter.isEmpty()) {
            results = bookRepository.findByAuthorContainingIgnoreCase(authorFilter, pageable);
        } else {
            results = bookRepository.findAll(pageable);
        }
        return results.map(this::toResponse);
    }

    public BookResponse findById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return toResponse(book);
    }

    @Transactional
    public BookResponse create(BookRequest request) {
        String title = cleanInput(request.title());
        String author = cleanInput(request.author());
        ensureTitleAndAuthorAreUnique(title, author, null);

        // A null ID means this is new; @GeneratedValue asks the database for its ID.
        Book savedBook = bookRepository.save(
                new Book(title, author, request.publicationYear()));
        return toResponse(savedBook);
    }

    @Transactional
    public BookResponse replace(long id, BookRequest request) {
        Book currentBook = findEntity(id);
        String title = cleanInput(request.title());
        String author = cleanInput(request.author());
        ensureTitleAndAuthorAreUnique(title, author, id);

        // currentBook is managed in this transaction; Hibernate tracks these field changes.
        currentBook.replaceDetails(title, author, request.publicationYear());
        return toResponse(currentBook);
    }

    @Transactional
    public BookResponse patch(long id, BookPatchRequest patch) {
        Book currentBook = findEntity(id);
        String title = patch.title() == null
                ? currentBook.getTitle() : cleanInput(patch.title());
        String author = patch.author() == null
                ? currentBook.getAuthor() : cleanInput(patch.author());
        int year = patch.publicationYear() == null
                ? currentBook.getPublicationYear() : patch.publicationYear();

        ensureTitleAndAuthorAreUnique(title, author, id);
        currentBook.replaceDetails(title, author, year);
        return toResponse(currentBook);
    }

    @Transactional
    public void delete(long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    private Book findEntity(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    private void ensureTitleAndAuthorAreUnique(String title, String author, Long excludedId) {
        boolean duplicateExists = excludedId == null
                ? bookRepository.existsByTitleIgnoreCaseAndAuthorIgnoreCase(title, author)
                : bookRepository.existsByTitleIgnoreCaseAndAuthorIgnoreCaseAndIdNot(
                        title, author, excludedId);
        if (duplicateExists) {
            throw new DuplicateBookException();
        }
    }

    private BookResponse toResponse(Book book) {
        // Map explicitly so JPA entities and lazy associations never leak through the API.
        return new BookResponse(
                book.getId(), book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    private String cleanInput(String value) {
        return value.trim();
    }

    private String cleanFilter(String value) {
        return value == null ? "" : value.trim();
    }
}

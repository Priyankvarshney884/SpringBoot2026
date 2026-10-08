package com.revision.springboot2026.service;

import com.revision.springboot2026.domain.Book;
import com.revision.springboot2026.dto.BookPatchRequest;
import com.revision.springboot2026.dto.BookRequest;
import com.revision.springboot2026.dto.BookResponse;
import com.revision.springboot2026.exception.BookNotFoundException;
import com.revision.springboot2026.exception.DuplicateBookException;
import com.revision.springboot2026.repository.BookRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

/** Applies book-related application rules and keeps storage details out of the controller. */
// @Service tells Spring to create this application-service bean.
@Service
public class BookService {
    private final BookRepository bookRepository;

    // Spring passes in the repository bean. The service does not create storage itself.
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /** Optional title/author filters; filtering the in-memory list takes O(n). */
    public List<BookResponse> search(String title, String author) {
        String titleFilter = normalizeFilter(title);
        String authorFilter = normalizeFilter(author);

        return bookRepository.findAll().stream()
                .filter(book -> titleFilter.isEmpty()
                        || normalize(book.getTitle()).contains(titleFilter))
                .filter(book -> authorFilter.isEmpty()
                        || normalize(book.getAuthor()).contains(authorFilter))
                .map(this::toResponse)
                .toList();
    }

    public BookResponse findById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return toResponse(book);
    }

    public BookResponse create(BookRequest request) {
        ensureTitleAndAuthorAreUnique(request, 0);
        Book newBook = new Book(0, request.title(), request.author(), request.publicationYear());
        Book savedBook = bookRepository.save(newBook);
        return toResponse(savedBook);
    }

    public BookResponse replace(long id, BookRequest request) {
        // Check existence first so an update never silently creates a new record.
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        ensureTitleAndAuthorAreUnique(request, id);

        Book replacement = new Book(id, request.title(), request.author(), request.publicationYear());
        Book savedBook = bookRepository.save(replacement);
        return toResponse(savedBook);
    }

    public BookResponse patch(long id, BookPatchRequest patch) {
        // PATCH needs the current values so omitted fields can be preserved.
        Book currentBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        String title = patch.title() == null ? currentBook.getTitle() : patch.title();
        String author = patch.author() == null ? currentBook.getAuthor() : patch.author();
        int year = patch.publicationYear() == null
                ? currentBook.getPublicationYear()
                : patch.publicationYear();

        BookRequest completeReplacement = new BookRequest(title, author, year);
        ensureTitleAndAuthorAreUnique(completeReplacement, id);

        Book replacement = new Book(id, title, author, year);
        return toResponse(bookRepository.save(replacement));
    }

    public void delete(long id) {
        if (!bookRepository.deleteById(id)) {
            throw new BookNotFoundException(id);
        }
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(), book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    /**
     * This simple in-memory exercise scans all books, so duplicate checking is O(n).
     * A database version can enforce this rule with a unique constraint/index instead.
     */
    private void ensureTitleAndAuthorAreUnique(BookRequest request, long bookBeingReplaced) {
        String requestedTitle = normalize(request.title());
        String requestedAuthor = normalize(request.author());

        boolean duplicateExists = bookRepository.findAll().stream()
                .anyMatch(book -> book.getId() != bookBeingReplaced
                        && normalize(book.getTitle()).equals(requestedTitle)
                        && normalize(book.getAuthor()).equals(requestedAuthor));

        if (duplicateExists) {
            throw new DuplicateBookException();
        }
    }

    private String normalize(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizeFilter(String value) {
        return value == null ? "" : normalize(value);
    }
}

package com.revision.springboot2026.service;

import com.revision.springboot2026.domain.Book;
import com.revision.springboot2026.dto.BookRequest;
import com.revision.springboot2026.dto.BookResponse;
import com.revision.springboot2026.repository.BookRepository;
import java.util.List;
import java.util.Optional;
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

    public Optional<BookResponse> findById(long id) {
        return bookRepository.findById(id).map(this::toResponse);
    }

    public BookResponse create(BookRequest request) {
        Book newBook = new Book(0, request.title(), request.author(), request.publicationYear());
        Book savedBook = bookRepository.save(newBook);
        return toResponse(savedBook);
    }

    public Optional<BookResponse> replace(long id, BookRequest request) {
        if (bookRepository.findById(id).isEmpty()) {
            return Optional.empty();
        }

        Book replacement = new Book(id, request.title(), request.author(), request.publicationYear());
        Book savedBook = bookRepository.save(replacement);
        return Optional.of(toResponse(savedBook));
    }

    public boolean delete(long id) {
        return bookRepository.deleteById(id);
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(), book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }
}

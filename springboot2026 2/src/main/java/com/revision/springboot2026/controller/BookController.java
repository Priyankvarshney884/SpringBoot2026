package com.revision.springboot2026.controller;

import com.revision.springboot2026.dto.BookRequest;
import com.revision.springboot2026.dto.BookResponse;
import com.revision.springboot2026.service.BookService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** HTTP endpoints for creating, reading, replacing, and deleting books. */
// Spring discovers this controller and writes its return values as HTTP response bodies.
@RestController
// All endpoint URLs in this class start with /api/books.
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    // Constructor injection: Spring provides the BookService bean when it creates this controller.
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET /api/books returns all books.
    @GetMapping
    public List<BookResponse> findAll() {
        return bookService.findAll();
    }

    // GET /api/books/1 returns book 1, or a 404 response if it does not exist.
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findById(
            // @PathVariable takes {id} from the URL and converts it from text to long.
            @PathVariable long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/books reads JSON into BookRequest and responds with status 201 Created.
    @PostMapping
    public ResponseEntity<BookResponse> create(
            // @RequestBody asks Spring to convert the JSON body into this Java record.
            @RequestBody BookRequest request) {
        BookResponse createdBook = bookService.create(request);
        URI location = URI.create("/api/books/" + createdBook.id());
        return ResponseEntity.created(location).body(createdBook);
    }

    // PUT /api/books/1 replaces all editable fields of book 1; missing IDs return 404.
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> replace(
            // The ID comes from the URL; the new field values come from the JSON body.
            @PathVariable long id,
            @RequestBody BookRequest request) {
        return bookService.replace(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/books/1 returns 204 when deleted, or 404 when there was no book 1.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            // Read the ID from the final URL segment, for example /api/books/1.
            @PathVariable long id) {
        if (!bookService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

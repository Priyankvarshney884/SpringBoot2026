package com.revision.springboot2026.controller;

import com.revision.springboot2026.dto.BookPatchRequest;
import com.revision.springboot2026.dto.BookRequest;
import com.revision.springboot2026.dto.BookResponse;
import com.revision.springboot2026.service.BookService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // Spring turns page, size, and sort query parameters into a Pageable value.
    @GetMapping
    public Page<BookResponse> findAll(
            // Defaults to page 0, 10 items, sorted by ID when the caller sends no options.
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return bookService.findAll(pageable);
    }

    // @RequestParam reads optional query-string filters, for example ?author=Bloch.
    // Filters, page limits, and ordering are executed by Spring Data in the database.
    // @GetMapping gives this search a separate route from the plain list route.
    @GetMapping("/search")
    public Page<BookResponse> search(
            // An absent parameter is null because required=false.
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return bookService.search(title, author, pageable);
    }

    // GET /api/books/1 returns book 1, or a 404 response if it does not exist.
    @GetMapping("/{id}")
    public BookResponse findById(
            // @PathVariable takes {id} from the URL and converts it from text to long.
            @PathVariable long id) {
        // A missing ID throws BookNotFoundException; ApiExceptionHandler maps it to HTTP 404.
        return bookService.findById(id);
    }

    // POST /api/books reads JSON into BookRequest and responds with status 201 Created.
    @PostMapping
    public ResponseEntity<BookResponse> create(
            // @Valid checks the DTO constraints before the service receives this request.
            @Valid
            // @RequestBody asks Spring to convert the JSON body into this Java record.
            @RequestBody BookRequest request) {
        BookResponse createdBook = bookService.create(request);
        URI location = URI.create("/api/books/" + createdBook.id());
        return ResponseEntity.created(location).body(createdBook);
    }

    // PUT /api/books/1 replaces all editable fields of book 1; missing IDs return 404.
    @PutMapping("/{id}")
    public BookResponse replace(
            // The ID comes from the URL; the new field values come from the JSON body.
            @PathVariable long id,
            // @Valid runs the BookRequest field constraints before this method is called.
            @Valid
            @RequestBody BookRequest request) {
        return bookService.replace(id, request);
    }

    // PATCH changes only the non-null fields in the JSON body; omitted fields are kept as-is.
    // Spring maps the PATCH HTTP method to this handler.
    @PatchMapping("/{id}")
    public BookResponse patch(
            @PathVariable long id,
            // Validate supplied fields, then convert the JSON body into BookPatchRequest.
            @Valid
            @RequestBody BookPatchRequest request) {
        return bookService.patch(id, request);
    }

    // DELETE /api/books/1 returns 204 when deleted, or 404 when there was no book 1.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            // Read the ID from the final URL segment, for example /api/books/1.
            @PathVariable long id) {
        // A missing ID throws BookNotFoundException; the advice creates its 404 response.
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

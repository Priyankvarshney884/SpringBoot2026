package com.revision.springboot2026.exception;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Converts application and request errors into safe, consistent HTTP responses. */
// Spring applies this advice to exceptions raised by any controller.
@RestControllerAdvice
public class ApiExceptionHandler {

    // Bean validation failures include field-level details we can safely show to the client.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException exception) {
        Map<String, String> fields = new TreeMap<>();
        // Keep one message per field and sort field names for predictable JSON output.
        exception.getBindingResult().getFieldErrors().forEach(error ->
                fields.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return response(HttpStatus.BAD_REQUEST, "Invalid request", fields);
    }

    // JSON parse errors and values that cannot convert to the DTO are client errors too.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableBody(
            HttpMessageNotReadableException exception) {
        return response(HttpStatus.BAD_REQUEST, "Request body is missing or contains invalid JSON or values", Map.of());
    }

    // These application exceptions are translated into HTTP meanings here, not in the service.
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(BookNotFoundException exception) {
        return response(HttpStatus.NOT_FOUND, exception.getMessage(), Map.of());
    }

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<ApiError> handleDuplicate(DuplicateBookException exception) {
        return response(HttpStatus.CONFLICT, exception.getMessage(), Map.of());
    }

    // Safety net: do not send exception messages or stack traces to API clients.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception exception) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", Map.of());
    }

    private ResponseEntity<ApiError> response(
            HttpStatus status, String message, Map<String, String> fields) {
        // Build one response shape for every error, including its HTTP status and optional field details.
        ApiError body = new ApiError(status.value(), status.getReasonPhrase(), message, fields);
        return ResponseEntity.status(status).body(body);
    }
}

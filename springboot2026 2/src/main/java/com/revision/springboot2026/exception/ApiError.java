package com.revision.springboot2026.exception;

import java.util.Map;

/** Safe, stable error shape returned by this API. */
// fieldErrors is empty for errors unrelated to a specific DTO field.
public record ApiError(int status, String error, String message, Map<String, String> fieldErrors) {
}

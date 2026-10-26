package com.example.Spring_starter.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Cart API response mein ek saved item ki detail. */
public record CartItemResponseDto(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

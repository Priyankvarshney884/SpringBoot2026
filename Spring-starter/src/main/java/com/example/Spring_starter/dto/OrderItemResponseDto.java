package com.example.Spring_starter.dto;

import java.math.BigDecimal;

/** Placed order ke ek product line ki API response detail. */
public record OrderItemResponseDto(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {
}

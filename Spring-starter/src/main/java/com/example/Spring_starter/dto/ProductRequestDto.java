package com.example.Spring_starter.dto;

import java.math.BigDecimal;

/** Client se product create/update ke liye aane wale fields ka API contract. */
public record ProductRequestDto(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category,
        String imageUrl,
        Boolean active
) {
}

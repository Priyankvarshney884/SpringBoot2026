package com.example.Spring_starter.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Client ko product read/create/update ke baad bheje jaane wale fields. */
public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category,
        String imageUrl,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

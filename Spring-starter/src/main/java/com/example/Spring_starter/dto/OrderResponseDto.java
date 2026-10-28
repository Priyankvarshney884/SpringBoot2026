package com.example.Spring_starter.dto;

import com.example.Spring_starter.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** Placed order ki summary aur line items ka API response. */
public record OrderResponseDto(
        Long id,
        Long userId,
        OrderStatus status,
        BigDecimal total,
        List<OrderItemResponseDto> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

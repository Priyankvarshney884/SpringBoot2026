package com.example.Spring_starter.dto;

/** Cart mein product add karne ke liye client se aane wali fields. */
public record CartItemRequestDto(
        Long productId,
        Integer quantity
) {
}

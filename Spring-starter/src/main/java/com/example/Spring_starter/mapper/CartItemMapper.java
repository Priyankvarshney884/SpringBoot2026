package com.example.Spring_starter.mapper;

import com.example.Spring_starter.dto.CartItemResponseDto;
import com.example.Spring_starter.model.CartItem;
import org.springframework.stereotype.Component;

/** CartItem entity ko API response DTO mein map karta hai. */
@Component
public class CartItemMapper {

    public CartItemResponseDto toResponse(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getPrice(),
                cartItem.getCreatedAt(),
                cartItem.getUpdatedAt()
        );
    }
}

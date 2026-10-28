package com.example.Spring_starter.mapper;

import com.example.Spring_starter.dto.OrderItemResponseDto;
import com.example.Spring_starter.dto.OrderResponseDto;
import com.example.Spring_starter.model.Order;
import org.springframework.stereotype.Component;

/** Order entity aur uske line items ko API response DTOs mein map karta hai. */
@Component
public class OrderMapper {

    public OrderResponseDto toResponse(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getUser().getId(),
                order.getStatus(),
                order.getTotal(),
                order.getItems().stream()
                        .map(item -> new OrderItemResponseDto(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getPrice()
                        ))
                        .toList(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}

package com.example.Spring_starter.controller;

import com.example.Spring_starter.dto.CartItemRequestDto;
import com.example.Spring_starter.dto.CartItemResponseDto;
import com.example.Spring_starter.mapper.CartItemMapper;
import com.example.Spring_starter.service.CartItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartItemService cartItemService;
    private final CartItemMapper cartItemMapper;

    public CartController(CartItemService cartItemService, CartItemMapper cartItemMapper) {
        this.cartItemService = cartItemService;
        this.cartItemMapper = cartItemMapper;
    }

    @PostMapping
    public ResponseEntity<CartItemResponseDto> addProductToCart(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CartItemRequestDto request
    ) {
        return ResponseEntity.ok(cartItemMapper.toResponse(
                cartItemService.addProductToCart(userId, request.productId(), request.quantity())
        ));
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getCart(
            @RequestHeader("X-User-Id") Long userId
    ) {
        List<CartItemResponseDto> items = cartItemService.getCartByUserId(userId).stream()
                .map(cartItemMapper::toResponse)
                .toList();
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductFromCart(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long productId
    ) {
        // User aur product ka matching cart item mila ho tabhi deletion successful hota hai.
        if (!cartItemService.removeProductFromCart(userId, productId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleNotFound(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleInvalidRequest(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Void> handleStockConflict(IllegalStateException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

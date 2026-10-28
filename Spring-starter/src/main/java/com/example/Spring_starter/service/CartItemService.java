package com.example.Spring_starter.service;

import com.example.Spring_starter.model.CartItem;
import com.example.Spring_starter.model.Product;
import com.example.Spring_starter.model.User;
import com.example.Spring_starter.repository.CartItemRepository;
import com.example.Spring_starter.repository.ProductRepository;
import com.example.Spring_starter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartItemService(
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            ProductRepository productRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartItem addProductToCart(Long userId, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        CartItem cartItem = cartItemRepository.findByUser_IdAndProduct_Id(userId, productId)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setUser(user);
                    newItem.setPrice(product.getPrice());
                    newItem.setQuantity(0);
                    return newItem;
                });

        // Response mapper ko transaction ke baad product fields padhne ke liye loaded entity dete hain.
        cartItem.setProduct(product);
        int updatedQuantity = cartItem.getQuantity() + quantity;
        if (product.getStock() == null || updatedQuantity > product.getStock()) {
            throw new IllegalStateException("Requested quantity exceeds available stock.");
        }

        // Existing product par quantity add hoti hai; naya item product ka current unit price rakhta hai.
        cartItem.setQuantity(updatedQuantity);
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public boolean removeProductFromCart(Long userId, Long productId) {
        return cartItemRepository.findByUser_IdAndProduct_Id(userId, productId)
                .map(cartItem -> {
                    cartItemRepository.delete(cartItem);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<CartItem> getCartByUserId(Long userId) {
        return cartItemRepository.findByUser_Id(userId);
    }
}

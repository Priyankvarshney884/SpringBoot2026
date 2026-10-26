package com.example.Spring_starter.repository;

import com.example.Spring_starter.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/** User aur product ke hisaab se persisted cart items access karta hai. */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser_Id(Long userId);

    Optional<CartItem> findByUser_IdAndProduct_Id(Long userId, Long productId);
}

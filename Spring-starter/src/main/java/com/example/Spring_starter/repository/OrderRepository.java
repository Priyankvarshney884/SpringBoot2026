package com.example.Spring_starter.repository;

import com.example.Spring_starter.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data JPA order persistence ke common CRUD methods provide karta hai. */
public interface OrderRepository extends JpaRepository<Order, Long> {
}

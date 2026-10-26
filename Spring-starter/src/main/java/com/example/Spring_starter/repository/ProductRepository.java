package com.example.Spring_starter.repository;

import com.example.Spring_starter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data JPA repository jo products ke common CRUD operations expose karti hai. */
public interface ProductRepository extends JpaRepository<Product, Long> {
}

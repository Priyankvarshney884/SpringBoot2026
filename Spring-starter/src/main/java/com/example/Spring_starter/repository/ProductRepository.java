package com.example.Spring_starter.repository;

import com.example.Spring_starter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Spring Data JPA repository jo products ke common CRUD operations expose karti hai. */
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Spring Data method name se partial, case-insensitive product-name search query banata hai.
    List<Product> findByNameContainingIgnoreCase(String name);
}

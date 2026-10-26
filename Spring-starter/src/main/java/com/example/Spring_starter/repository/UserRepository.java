package com.example.Spring_starter.repository;

import com.example.Spring_starter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA is interface ke liye common User CRUD operations implement karta hai.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}

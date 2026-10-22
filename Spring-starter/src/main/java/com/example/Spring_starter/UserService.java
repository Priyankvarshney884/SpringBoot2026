package com.example.Spring_starter;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Learning example: app restart par yeh in-memory list reset ho jaati hai.
    private final List<User> users = new ArrayList<>();
    private long nextId = 1;

    public List<User> getAllUsers() {
        return users;
    }

    public User addUser(User user) {
        // Har naye user ko unique ID dekar in-memory list mein rakhte hain.
        user.setId(nextId++);
        users.add(user);
        return user;
    }

    public Optional<User> findUserById(Long id) {
        // Stream list ko pipeline mein process karke pehla matching user return karti hai.
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        // Pehle existing user verify karte hain; missing ID par empty result bhejte hain.
        return findUserById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            return existingUser;
        });
    }
}

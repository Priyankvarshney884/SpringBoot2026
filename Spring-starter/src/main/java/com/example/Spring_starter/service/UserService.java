package com.example.Spring_starter.service;

import com.example.Spring_starter.model.User;
import com.example.Spring_starter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        // Naye record ka ID database generate kare, request body ka ID nahi.
        user.setId(null);
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        // Missing ID par empty result rahega; existing record ko save karke update persist hota hai.
        return findUserById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            return userRepository.save(existingUser);
        });
    }
}

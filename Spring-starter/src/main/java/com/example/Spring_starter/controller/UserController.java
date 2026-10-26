package com.example.Spring_starter.controller;

import com.example.Spring_starter.model.User;
import com.example.Spring_starter.dto.UserRequestDto;
import com.example.Spring_starter.dto.UserResponseDto;
import com.example.Spring_starter.mapper.UserMapper;
import com.example.Spring_starter.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        // Entity ko response DTO mein map karke persistence fields API se alag rakhte hain.
        return userService.getAllUsers().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @PostMapping
    public UserResponseDto addUser(@RequestBody UserRequestDto request) {
        // Request DTO ko entity mein map karke service se persist karte hain.
        User user = userMapper.toEntity(request);
        return userMapper.toResponse(userService.addUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        // Missing entity par 404; milne par API response DTO return hota hai.
        return userService.findUserById(id)
                .map(userMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto request
    ) {
        // Request DTO ko entity mein map karke existing record update karte hain.
        User updatedUser = userMapper.toEntity(request);
        return userService.updateUser(id, updatedUser)
                .map(userMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

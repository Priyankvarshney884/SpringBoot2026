package com.example.Spring_starter;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** Keeps users in memory for the lifetime of the running application. */
@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return List.copyOf(users);
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }
}

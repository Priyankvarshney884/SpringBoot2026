package com.example.Spring_starter;

/**
 * User API ke ek user ka simple learning model.
 * Fields ko course ke exact request/response contract milne par adjust kar sakte hain.
 */
public class User {

    private Long id;
    private String name;

    public User() {
        // Default constructor JSON request body ko Java object mein bind karne mein kaam aata hai.
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

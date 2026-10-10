package com.revision.springboot2026.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/** Minimal related entity for learning one-to-many and fetch behavior. */
@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One publisher can have many books; collections are LAZY by default, stated explicitly here.
    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    protected Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return List.copyOf(books);
    }
}

package com.moviebooking.entity;

import jakarta.persistence.*;

// Entity Genre ánh xạ với bảng genres
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên thể loại: Action, Comedy, Horror...
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    public Genre() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
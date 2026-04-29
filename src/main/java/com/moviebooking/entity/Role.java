package com.moviebooking.entity;

import jakarta.persistence.*;

// Entity Role ánh xạ với bảng roles
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ví dụ: ADMIN, STAFF, CUSTOMER
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    public Role() {
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
package com.moviebooking.repository;

import com.moviebooking.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Tìm role theo tên, ví dụ: CUSTOMER, STAFF, ADMIN
    Optional<Role> findByName(String name);
}
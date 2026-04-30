package com.moviebooking.repository;

import com.moviebooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm user theo email, dùng cho login sau này
    Optional<User> findByEmail(String email);
    // Kiểm tra email đã tồn tại chưa, dùng cho register
    boolean existsByEmail(String email);
}
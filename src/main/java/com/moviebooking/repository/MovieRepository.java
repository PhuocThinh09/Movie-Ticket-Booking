package com.moviebooking.repository;

import com.moviebooking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository là tầng giao tiếp với database
// JpaRepository<Movie, Long> nghĩa là repository này quản lý Entity Movie
// và kiểu dữ liệu của khóa chính là Long.
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
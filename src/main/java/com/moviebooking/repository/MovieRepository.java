package com.moviebooking.repository;

import com.moviebooking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Lấy các phim chưa bị xóa mềm
    List<Movie> findByActiveTrue();

    // Kiểm tra trùng title khi create
    boolean existsByTitleIgnoreCase(String title);

    // Kiểm tra trùng title khi update, trừ chính movie hiện tại
    boolean existsByTitleIgnoreCaseAndIdNot(String title, Long id);
}
package com.moviebooking.config;

import com.moviebooking.entity.Movie;
import com.moviebooking.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration đánh dấu đây là class cấu hình của Spring
@Configuration
public class DataSeederConfig {

    // CommandLineRunner sẽ chạy sau khi Spring Boot start xong
    // Mục tiêu: insert dữ liệu mẫu vào DB để test entity + repository
    @Bean
    public CommandLineRunner seedMovieData(MovieRepository movieRepository) {
        return args -> {

            // Chỉ seed khi bảng movies đang trống
            // Tránh mỗi lần chạy app lại insert trùng dữ liệu
            if (movieRepository.count() == 0) {

                Movie movie = new Movie(
                        "Avengers: Endgame",
                        181,
                        "Action"
                );

                movieRepository.save(movie);

                System.out.println(">>> Seed movie thành công!");
            } else {
                System.out.println(">>> Bảng movies đã có dữ liệu, bỏ qua seed.");
            }
        };
    }
}
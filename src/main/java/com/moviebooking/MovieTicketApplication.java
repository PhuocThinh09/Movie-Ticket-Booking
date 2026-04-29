package com.moviebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Đánh dấu đây là class khởi động chính của ứng dụng Spring Boot
@SpringBootApplication
public class MovieTicketApplication {

    // Phương thức main là điểm bắt đầu của chương trình
    public static void main(String[] args) {

        // Khởi động ứng dụng Spring Boot
        SpringApplication.run(MovieTicketApplication.class, args);
    }
}
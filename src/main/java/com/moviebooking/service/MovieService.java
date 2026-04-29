package com.moviebooking.service;

import com.moviebooking.dto.MovieResponseDto;
import com.moviebooking.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Đánh dấu đây là service để xử lý logic nghiệp vụ
@Service
public class MovieService {

    // Method lấy danh sách movie dummy
    public List<MovieResponseDto> getAllMovies() {

        // Giả lập dữ liệu như thể lấy từ database
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "Avengers: Endgame", 181, "Action"));
        movies.add(new Movie( "Joker", 122, "Drama"));
        movies.add(new Movie( "Frozen 2", 103, "Animation"));

        // Chuyển từ model Movie sang DTO để trả ra ngoài
        List<MovieResponseDto> response = new ArrayList<>();
        for (Movie movie : movies) {
            response.add(new MovieResponseDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getGenre()
            ));
        }

        return response;
    }
}
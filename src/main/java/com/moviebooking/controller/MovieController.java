package com.moviebooking.controller;

import com.moviebooking.dto.response.MovieResponseDto;
import com.moviebooking.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// REST controller nhận request liên quan đến movie
@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Endpoint lấy danh sách movie
    @GetMapping("/api/movies")
    public List<MovieResponseDto> getAllMovies() {
        return movieService.getAllMovies();
    }
}
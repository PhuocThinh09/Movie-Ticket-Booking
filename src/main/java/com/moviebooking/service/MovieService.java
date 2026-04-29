package com.moviebooking.service;

import com.moviebooking.dto.response.MovieResponseDto;
import com.moviebooking.entity.Genre;
import com.moviebooking.entity.Movie;
import com.moviebooking.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieResponseDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    private MovieResponseDto toResponseDto(Movie movie) {
        String genreNames = movie.getGenres()
                .stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", "));

        return new MovieResponseDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDuration(),
                genreNames
        );
    }
}
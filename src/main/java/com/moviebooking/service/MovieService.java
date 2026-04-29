package com.moviebooking.service;

import com.moviebooking.dto.request.MovieRequest;
import com.moviebooking.dto.response.MovieResponse;

import java.util.List;

public interface MovieService {

    List<MovieResponse> getAllMovies();

    MovieResponse getMovieById(Long id);

    MovieResponse createMovie(MovieRequest request);

    MovieResponse updateMovie(Long id, MovieRequest request);

    void deleteMovie(Long id);
}
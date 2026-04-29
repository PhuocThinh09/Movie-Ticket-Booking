package com.moviebooking.mapper;

import com.moviebooking.dto.request.MovieRequest;
import com.moviebooking.dto.response.MovieResponse;
import com.moviebooking.entity.Genre;
import com.moviebooking.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public Movie toEntity(MovieRequest request) {
        Movie movie = new Movie();

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setDuration(request.getDuration());
        movie.setRating(request.getRating());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setStatus(request.getStatus());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setLanguage(request.getLanguage());
        movie.setActive(true);

        return movie;
    }

    public void updateEntity(Movie movie, MovieRequest request) {
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setDuration(request.getDuration());
        movie.setRating(request.getRating());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setStatus(request.getStatus());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setLanguage(request.getLanguage());
    }

    public MovieResponse toResponse(Movie movie) {
        MovieResponse response = new MovieResponse();

        response.setId(movie.getId());
        response.setTitle(movie.getTitle());
        response.setDescription(movie.getDescription());
        response.setDuration(movie.getDuration());
        response.setRating(movie.getRating());
        response.setReleaseDate(movie.getReleaseDate());
        response.setStatus(movie.getStatus());
        response.setPosterUrl(movie.getPosterUrl());
        response.setLanguage(movie.getLanguage());
        response.setActive(movie.getActive());
        response.setCreatedAt(movie.getCreatedAt());
        response.setUpdatedAt(movie.getUpdatedAt());

        if (movie.getGenres() != null) {
            response.setGenres(
                    movie.getGenres()
                            .stream()
                            .map(Genre::getName)
                            .collect(Collectors.toList())
            );
        }

        return response;
    }
}
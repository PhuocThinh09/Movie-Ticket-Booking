package com.moviebooking.service.impl;

import com.moviebooking.dto.request.MovieRequest;
import com.moviebooking.dto.response.MovieResponse;
import com.moviebooking.entity.Genre;
import com.moviebooking.entity.Movie;
import com.moviebooking.mapper.MovieMapper;
import com.moviebooking.repository.GenreRepository;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.service.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieMapper movieMapper;

    public MovieServiceImpl(
            MovieRepository movieRepository,
            GenreRepository genreRepository,
            MovieMapper movieMapper
    ) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findByActiveTrue()
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponse getMovieById(Long id) {
        Movie movie = findMovieById(id);
        return movieMapper.toResponse(movie);
    }

    @Override
    @Transactional
    public MovieResponse createMovie(MovieRequest request) {
        validateCreateRequest(request);

        Movie movie = movieMapper.toEntity(request);

        Set<Genre> genres = getGenresFromIds(request.getGenreIds());
        movie.setGenres(genres);

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toResponse(savedMovie);
    }

    @Override
    @Transactional
    public MovieResponse updateMovie(Long id, MovieRequest request) {
        Movie movie = findMovieById(id);

        validateUpdateRequest(id, request);

        movieMapper.updateEntity(movie, request);

        Set<Genre> genres = getGenresFromIds(request.getGenreIds());
        movie.setGenres(genres);

        Movie updatedMovie = movieRepository.save(movie);

        return movieMapper.toResponse(updatedMovie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        Movie movie = findMovieById(id);

        // Xóa mềm: không xóa record khỏi database
        movie.setActive(false);

        movieRepository.save(movie);
    }

    private Movie findMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    private Set<Genre> getGenresFromIds(List<Long> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return new HashSet<>();
        }

        List<Genre> genres = genreRepository.findAllById(genreIds);

        if (genres.size() != genreIds.size()) {
            throw new RuntimeException("Some genres do not exist");
        }

        return new HashSet<>(genres);
    }

    private void validateCreateRequest(MovieRequest request) {
        validateBasicMovieData(request);

        if (movieRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new RuntimeException("Movie title already exists");
        }
    }

    private void validateUpdateRequest(Long id, MovieRequest request) {
        validateBasicMovieData(request);

        if (movieRepository.existsByTitleIgnoreCaseAndIdNot(request.getTitle(), id)) {
            throw new RuntimeException("Movie title already exists");
        }
    }

    private void validateBasicMovieData(MovieRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Movie title is required");
        }

        if (request.getDuration() == null || request.getDuration() <= 0) {
            throw new RuntimeException("Movie duration must be greater than 0");
        }

        if (request.getStatus() == null || request.getStatus().trim().isEmpty()) {
            request.setStatus("COMING_SOON");
        }
    }
}
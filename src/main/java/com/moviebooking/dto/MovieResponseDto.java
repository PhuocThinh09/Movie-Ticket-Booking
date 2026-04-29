package com.moviebooking.dto;

// DTO dùng để trả dữ liệu movie ra ngoài API
public class MovieResponseDto {

    private Long id;
    private String title;
    private String genre;

    public MovieResponseDto(Long id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }
}
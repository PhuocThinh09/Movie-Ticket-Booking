package com.moviebooking.dto.response;

public class MovieResponseDto {

    private Long id;
    private String title;
    private Integer duration;
    private String genres;

    public MovieResponseDto(Long id, String title, Integer duration, String genres) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getGenres() {
        return genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
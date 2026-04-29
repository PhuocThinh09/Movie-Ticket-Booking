package com.moviebooking.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// Entity Movie ánh xạ với bảng movies trong MySQL
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên phim, bắt buộc nhập
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    // Mô tả phim
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Thời lượng phim, tính bằng phút
    @Column(name = "duration", nullable = false)
    private Integer duration;

    // Rating ví dụ: PG, PG-13, R, C13, C16
    @Column(name = "rating", length = 10)
    private String rating;

    // Ngày phát hành
    @Column(name = "release_date")
    private LocalDate releaseDate;

    // Trạng thái phim: COMING_SOON, NOW_SHOWING, ENDED
    @Column(name = "status", length = 30)
    private String status = "COMING_SOON";

    // Link poster phim
    @Column(name = "poster_url", length = 500)
    private String posterUrl;

    // Ngôn ngữ phim
    @Column(name = "language", length = 50)
    private String language;

    // Soft delete / active flag
    @Column(name = "is_active")
    private Boolean active = true;

    // Thời điểm tạo
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Thời điểm cập nhật
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Movie - Genre là quan hệ nhiều-nhiều thông qua bảng movie_genres
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    public Movie() {
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
}
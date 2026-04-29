package com.moviebooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity cho JPA biết class này sẽ được ánh xạ thành bảng trong database
@Entity

// Tên bảng trong MySQL sẽ là movies
@Table(name = "movies")
public class Movie {

    // Khóa chính của bảng movies
    @Id

    // ID tự tăng theo cơ chế AUTO_INCREMENT của MySQL
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên phim, bắt buộc có dữ liệu
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    // Thời lượng phim, tính bằng phút
    @Column(name = "duration", nullable = false)
    private Integer duration;

    // Thể loại phim
    @Column(name = "genre", nullable = false, length = 100)
    private String genre;

    // Constructor rỗng là bắt buộc cho JPA/Hibernate
    public Movie() {
    }

    // Constructor dùng khi tạo movie mới trong code
    public Movie(String title, Integer duration, String genre) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    // Setter ID vẫn nên có để framework hoặc test có thể dùng khi cần
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
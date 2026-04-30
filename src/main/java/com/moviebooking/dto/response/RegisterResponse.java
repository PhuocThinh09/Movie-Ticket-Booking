package com.moviebooking.dto.response;

import java.util.List;

// DTO trả kết quả đăng ký cho client
public class RegisterResponse {

    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private String status;
    private List<String> roles;

    public RegisterResponse() {
    }

    public RegisterResponse(
            Long id,
            String email,
            String fullName,
            String phone,
            String status,
            List<String> roles
    ) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.status = status;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
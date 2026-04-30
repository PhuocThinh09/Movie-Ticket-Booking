package com.moviebooking.service;

import com.moviebooking.dto.request.RegisterRequest;
import com.moviebooking.dto.response.RegisterResponse;

public interface AuthService {

    // Đăng ký tài khoản customer mới
    RegisterResponse register(RegisterRequest request);
}
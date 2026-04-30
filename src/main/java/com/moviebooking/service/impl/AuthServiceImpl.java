package com.moviebooking.service.impl;

import com.moviebooking.dto.request.RegisterRequest;
import com.moviebooking.dto.response.RegisterResponse;
import com.moviebooking.entity.Role;
import com.moviebooking.entity.User;
import com.moviebooking.repository.RoleRepository;
import com.moviebooking.repository.UserRepository;
import com.moviebooking.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

    private static final String DEFAULT_CUSTOMER_ROLE = "CUSTOMER";

    // Regex email cơ bản, đủ dùng cho project học tập
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        // 1. Validate dữ liệu đầu vào
        validateRegisterRequest(request);

        // 2. Chuẩn hóa email: bỏ khoảng trắng, đưa về lowercase
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        // 3. Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new RuntimeException("Email already exists");
        }

        // 4. Mã hóa password bằng BCrypt
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 5. Lấy hoặc tạo role CUSTOMER
        Role customerRole = getOrCreateCustomerRole();

        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

        // 6. Tạo User entity để lưu xuống database
        User user = new User();
        user.setEmail(normalizedEmail);
        user.setPassword(encodedPassword);
        user.setFullName(request.getFullName().trim());
        user.setPhone(normalizeOptionalText(request.getPhone()));
        user.setStatus("ACTIVE");
        user.setRoles(roles);

        // 7. Lưu user xuống database
        User savedUser = userRepository.save(user);

        // 8. Convert User entity sang RegisterResponse
        return toRegisterResponse(savedUser);
    }

    private void validateRegisterRequest(RegisterRequest request) {
        if (request == null) {
            throw new RuntimeException("Register request is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        String email = request.getEmail().trim();

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new RuntimeException("Email format is invalid");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        if (request.getPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new RuntimeException("Full name is required");
        }
    }

    private Role getOrCreateCustomerRole() {
        return roleRepository.findByName(DEFAULT_CUSTOMER_ROLE)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(DEFAULT_CUSTOMER_ROLE);
                    return roleRepository.save(role);
                });
    }

    private RegisterResponse toRegisterResponse(User user) {
        List<String> roleNames = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return new RegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getStatus(),
                roleNames
        );
    }

    private String normalizeOptionalText(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }
}
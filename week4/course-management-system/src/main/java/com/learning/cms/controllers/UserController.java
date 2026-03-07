package com.learning.cms.controllers;

import com.learning.cms.dto.request.LoginRequestDTO;
import com.learning.cms.dto.request.RegisterRequestDTO;
import com.learning.cms.dto.response.ApiResponse;
import com.learning.cms.dto.response.UserResponseDTO;
import com.learning.cms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User registration, login and profile APIs")
public class UserController {

    private final UserService userService;

    @PostMapping("/api/auth/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO dto) {
        UserResponseDTO user = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", user));
    }

    @PostMapping("/api/auth/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<ApiResponse<UserResponseDTO>> login(@Valid @RequestBody LoginRequestDTO dto) {
        UserResponseDTO user = userService.login(dto);
        return ResponseEntity.ok(ApiResponse.success("Login successful", user));
    }

    @GetMapping("/api/users/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("User fetched", userService.getUserById(id)));
    }

    @PostMapping("/api/users/{id}/profile-picture")
    @Operation(summary = "Upload profile picture")
    public ResponseEntity<ApiResponse<UserResponseDTO>> uploadProfilePicture(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.success("Profile picture updated", userService.uploadProfilePicture(id, file)));
    }
}

package com.learning.cms.service.impl;

import com.learning.cms.dto.request.LoginRequestDTO;
import com.learning.cms.dto.request.RegisterRequestDTO;
import com.learning.cms.dto.response.UserResponseDTO;
import com.learning.cms.entity.User;
import com.learning.cms.exception.InvalidRequestException;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.repository.UserRepository;
import com.learning.cms.service.UserService;
import com.learning.cms.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileStorageUtil fileStorageUtil;

    @Override
    public UserResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new InvalidRequestException("Email already registered: " + dto.getEmail());
        }

        User user = User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                // NOTE: In production, encode the password with BCrypt
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();

        User saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    @Override
    public UserResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + dto.getEmail()));

        // NOTE: In production, use BCrypt password matching
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new InvalidRequestException("Invalid password");
        }

        return mapToDTO(user);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToDTO(user);
    }

    @Override
    public UserResponseDTO uploadProfilePicture(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        String fileName = fileStorageUtil.storeFile(file);
        user.setProfilePicture(fileName);
        User saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    private UserResponseDTO mapToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .profilePicture(user.getProfilePicture())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

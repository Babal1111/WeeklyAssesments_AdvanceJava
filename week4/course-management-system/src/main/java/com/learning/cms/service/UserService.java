package com.learning.cms.service;

import com.learning.cms.dto.request.LoginRequestDTO;
import com.learning.cms.dto.request.RegisterRequestDTO;
import com.learning.cms.dto.response.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponseDTO register(RegisterRequestDTO dto);
    UserResponseDTO login(LoginRequestDTO dto);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO uploadProfilePicture(Long userId, MultipartFile file);
}

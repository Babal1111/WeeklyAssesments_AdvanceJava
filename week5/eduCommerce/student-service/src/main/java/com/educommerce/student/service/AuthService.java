package com.educommerce.student.service;

import com.educommerce.student.dto.AuthDto;
import com.educommerce.student.entity.Student;
import com.educommerce.student.repository.StudentRepository;
import com.educommerce.student.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthDto.AuthResponse register(AuthDto.RegisterRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered: " + request.getEmail());
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setDepartment(request.getDepartment());
        student.setSemester(request.getSemester());

        studentRepository.save(student);

        String token = jwtUtil.generateToken(student.getEmail());
        return new AuthDto.AuthResponse(token, student.getEmail(), student.getName(), "Registration successful");
    }

    public AuthDto.AuthResponse login(AuthDto.LoginRequest request) {
        Student student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Student not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(student.getEmail());
        return new AuthDto.AuthResponse(token, student.getEmail(), student.getName(), "Login successful");
    }
}

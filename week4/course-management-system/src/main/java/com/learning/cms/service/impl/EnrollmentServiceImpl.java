package com.learning.cms.service.impl;

import com.learning.cms.dto.request.EnrollmentRequestDTO;
import com.learning.cms.dto.response.EnrollmentResponseDTO;
import com.learning.cms.entity.Course;
import com.learning.cms.entity.Enrollment;
import com.learning.cms.entity.User;
import com.learning.cms.exception.InvalidRequestException;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.repository.CourseRepository;
import com.learning.cms.repository.EnrollmentRepository;
import com.learning.cms.repository.UserRepository;
import com.learning.cms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public EnrollmentResponseDTO enroll(EnrollmentRequestDTO dto) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(dto.getStudentId(), dto.getCourseId())) {
            throw new InvalidRequestException("Student is already enrolled in this course");
        }

        User student = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + dto.getStudentId()));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + dto.getCourseId()));

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();

        return mapToDTO(enrollmentRepository.save(enrollment));
    }

    @Override
    public List<EnrollmentResponseDTO> getStudentEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResponseDTO> getCourseEnrollments(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponseDTO updateProgress(Long enrollmentId, Double progress) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + enrollmentId));
        enrollment.setProgressPercentage(progress);
        return mapToDTO(enrollmentRepository.save(enrollment));
    }

    private EnrollmentResponseDTO mapToDTO(Enrollment e) {
        return EnrollmentResponseDTO.builder()
                .id(e.getId())
                .courseTitle(e.getCourse().getTitle())
                .studentName(e.getStudent().getFullName())
                .status(e.getStatus())
                .progressPercentage(e.getProgressPercentage())
                .enrollmentDate(e.getEnrollmentDate())
                .build();
    }
}

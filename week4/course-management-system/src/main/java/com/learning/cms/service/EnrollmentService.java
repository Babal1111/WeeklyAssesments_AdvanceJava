package com.learning.cms.service;

import com.learning.cms.dto.request.EnrollmentRequestDTO;
import com.learning.cms.dto.response.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponseDTO enroll(EnrollmentRequestDTO dto);
    List<EnrollmentResponseDTO> getStudentEnrollments(Long studentId);
    List<EnrollmentResponseDTO> getCourseEnrollments(Long courseId);
    EnrollmentResponseDTO updateProgress(Long enrollmentId, Double progress);
}

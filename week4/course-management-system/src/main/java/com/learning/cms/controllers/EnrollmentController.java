package com.learning.cms.controllers;

import com.learning.cms.dto.request.EnrollmentRequestDTO;
import com.learning.cms.dto.response.ApiResponse;
import com.learning.cms.dto.response.EnrollmentResponseDTO;
import com.learning.cms.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment Management", description = "APIs for managing student enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "Enroll a student in a course")
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> enroll(@Valid @RequestBody EnrollmentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Enrolled successfully", enrollmentService.enroll(dto)));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all enrollments for a student")
    public ResponseEntity<ApiResponse<List<EnrollmentResponseDTO>>> getStudentEnrollments(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success("Enrollments fetched", enrollmentService.getStudentEnrollments(studentId)));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get all enrollments for a course")
    public ResponseEntity<ApiResponse<List<EnrollmentResponseDTO>>> getCourseEnrollments(@PathVariable Long courseId) {
        return ResponseEntity.ok(ApiResponse.success("Enrollments fetched", enrollmentService.getCourseEnrollments(courseId)));
    }

    @PatchMapping("/{enrollmentId}/progress")
    @Operation(summary = "Update student progress in a course")
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> updateProgress(
            @PathVariable Long enrollmentId,
            @RequestParam Double progress) {
        return ResponseEntity.ok(ApiResponse.success("Progress updated", enrollmentService.updateProgress(enrollmentId, progress)));
    }
}

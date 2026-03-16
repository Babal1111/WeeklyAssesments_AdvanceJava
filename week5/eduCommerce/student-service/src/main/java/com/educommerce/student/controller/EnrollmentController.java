package com.educommerce.student.controller;

import com.educommerce.student.entity.Course;
import com.educommerce.student.entity.Enrollment;
import com.educommerce.student.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<Enrollment> enroll(@RequestBody Map<String, Long> request) {
        Long studentId = request.get("studentId");
        Long courseId = request.get("courseId");
        return ResponseEntity.ok(enrollmentService.enroll(studentId, courseId));
    }

    @GetMapping("/students/{id}/courses")
    public ResponseEntity<List<Course>> getCoursesForStudent(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getCoursesForStudent(id));
    }
}

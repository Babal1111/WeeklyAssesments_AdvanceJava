package com.educommerce.student.service;

import com.educommerce.student.entity.Course;
import com.educommerce.student.entity.Enrollment;
import com.educommerce.student.repository.CourseRepository;
import com.educommerce.student.repository.EnrollmentRepository;
import com.educommerce.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public Enrollment enroll(Long studentId, Long courseId) {
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        return enrollmentRepository.save(enrollment);
    }

    public List<Course> getCoursesForStudent(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(e -> courseRepository.findById(e.getCourseId()).orElse(null))
                .filter(c -> c != null)
                .collect(Collectors.toList());
    }
}

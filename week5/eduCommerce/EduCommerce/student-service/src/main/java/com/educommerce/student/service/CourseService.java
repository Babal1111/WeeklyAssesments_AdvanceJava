package com.educommerce.student.service;

import com.educommerce.student.entity.Course;
import com.educommerce.student.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Course existing = getCourseById(id);
        existing.setCourseName(updatedCourse.getCourseName());
        existing.setCourseCode(updatedCourse.getCourseCode());
        existing.setInstructor(updatedCourse.getInstructor());
        existing.setCredits(updatedCourse.getCredits());
        return courseRepository.save(existing);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}

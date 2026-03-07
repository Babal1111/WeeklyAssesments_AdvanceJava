package com.learning.cms.service.impl;

import com.learning.cms.dto.request.CourseRequestDTO;
import com.learning.cms.dto.response.CourseResponseDTO;
import com.learning.cms.entity.Course;
import com.learning.cms.entity.User;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.repository.CourseRepository;
import com.learning.cms.repository.UserRepository;
import com.learning.cms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public CourseResponseDTO createCourse(CourseRequestDTO dto) {
        User instructor = userRepository.findById(dto.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + dto.getInstructorId()));

        Course course = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .level(dto.getLevel())
                .instructor(instructor)
                .build();

        return mapToDTO(courseRepository.save(course));
    }

    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        course.setDuration(dto.getDuration());
        course.setLevel(dto.getLevel());

        return mapToDTO(courseRepository.save(course));
    }

    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    @Cacheable("courses")
    public Page<CourseResponseDTO> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    private CourseResponseDTO mapToDTO(Course course) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .price(course.getPrice())
                .duration(course.getDuration())
                .level(course.getLevel())
                .instructorName(course.getInstructor() != null ? course.getInstructor().getFullName() : null)
                .createdAt(course.getCreatedAt())
                .build();
    }
}

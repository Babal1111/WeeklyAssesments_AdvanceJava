package com.learning.cms.service;

import com.learning.cms.dto.request.CourseRequestDTO;
import com.learning.cms.dto.response.CourseResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseResponseDTO createCourse(CourseRequestDTO dto);
    CourseResponseDTO updateCourse(Long id, CourseRequestDTO dto);
    void deleteCourse(Long id);
    Page<CourseResponseDTO> getAllCourses(Pageable pageable);
    CourseResponseDTO getCourseById(Long id);
}

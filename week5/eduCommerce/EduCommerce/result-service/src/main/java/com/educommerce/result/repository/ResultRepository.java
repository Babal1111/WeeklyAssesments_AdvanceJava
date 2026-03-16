package com.educommerce.result.repository;

import com.educommerce.result.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudentId(Long studentId);
    List<Result> findByCourseId(Long courseId);
    List<Result> findByStudentIdAndCourseId(Long studentId, Long courseId);
}

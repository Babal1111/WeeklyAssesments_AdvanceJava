package com.educommerce.result.controller;

import com.educommerce.result.dto.ResultRequest;
import com.educommerce.result.dto.StudentPerformanceDto;
import com.educommerce.result.entity.Result;
import com.educommerce.result.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @PostMapping
    public ResponseEntity<Result> createResult(@RequestBody ResultRequest request) {
        return ResponseEntity.ok(resultService.createResult(request));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Result>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(resultService.getResultsByStudent(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Result>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(resultService.getResultsByCourse(courseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateResult(@PathVariable Long id, @RequestBody ResultRequest request) {
        return ResponseEntity.ok(resultService.updateResult(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return ResponseEntity.ok("Result deleted successfully");
    }

    @GetMapping("/performance/student/{studentId}/course/{courseId}")
    public ResponseEntity<StudentPerformanceDto> getPerformance(@PathVariable Long studentId,
                                                                 @PathVariable Long courseId) {
        return ResponseEntity.ok(resultService.getStudentPerformance(studentId, courseId));
    }
}

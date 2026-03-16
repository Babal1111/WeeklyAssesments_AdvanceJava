package com.educommerce.result.service;

import com.educommerce.result.client.AttendanceClient;
import com.educommerce.result.client.StudentClient;
import com.educommerce.result.dto.ResultRequest;
import com.educommerce.result.dto.StudentPerformanceDto;
import com.educommerce.result.entity.Result;
import com.educommerce.result.repository.ResultRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {

    private final ResultRepository resultRepository;
    private final StudentClient studentClient;
    private final AttendanceClient attendanceClient;

    public Result createResult(ResultRequest request) {
        Result result = new Result();
        result.setStudentId(request.getStudentId());
        result.setCourseId(request.getCourseId());
        result.setExamType(request.getExamType());
        result.setMarksObtained(request.getMarksObtained());
        result.setMaxMarks(request.getMaxMarks());
        return resultRepository.save(result);
    }

    public List<Result> getResultsByStudent(Long studentId) {
        return resultRepository.findByStudentId(studentId);
    }

    public List<Result> getResultsByCourse(Long courseId) {
        return resultRepository.findByCourseId(courseId);
    }

    public Result updateResult(Long id, ResultRequest request) {
        Result existing = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with id: " + id));
        existing.setExamType(request.getExamType());
        existing.setMarksObtained(request.getMarksObtained());
        existing.setMaxMarks(request.getMaxMarks());
        return resultRepository.save(existing);
    }

    public void deleteResult(Long id) {
        if (!resultRepository.existsById(id)) {
            throw new RuntimeException("Result not found with id: " + id);
        }
        resultRepository.deleteById(id);
    }

    @CircuitBreaker(name = "student-cb", fallbackMethod = "getPerformanceFallback")
    public StudentPerformanceDto getStudentPerformance(Long studentId, Long courseId) {
        StudentClient.StudentResponse student = studentClient.getStudentById(studentId);
        List<Result> results = resultRepository.findByStudentIdAndCourseId(studentId, courseId);

        double avgPct = results.stream()
                .filter(r -> r.getMaxMarks() != null && r.getMaxMarks() > 0)
                .mapToDouble(r -> (r.getMarksObtained() / r.getMaxMarks()) * 100)
                .average().orElse(0.0);

        String attendanceSummary = getAttendanceSummaryWithCB(studentId, courseId);

        return new StudentPerformanceDto(studentId, student.getName(), results, avgPct, attendanceSummary);
    }

    @CircuitBreaker(name = "attendance-cb", fallbackMethod = "attendanceFallback")
    public String getAttendanceSummaryWithCB(Long studentId, Long courseId) {
        AttendanceClient.AttendanceSummary summary = attendanceClient.getAttendanceSummary(studentId, courseId);
        return String.format("%.1f%% (%d/%d classes)",
                summary.getAttendancePercentage(),
                summary.getPresentCount(),
                summary.getTotalClasses());
    }

    public String attendanceFallback(Long studentId, Long courseId, Throwable ex) {
        log.warn("Attendance service unavailable for student {}, course {}: {}", studentId, courseId, ex.getMessage());
        return "Attendance data currently unavailable";
    }

    public StudentPerformanceDto getPerformanceFallback(Long studentId, Long courseId, Throwable ex) {
        log.warn("Student service unavailable: {}", ex.getMessage());
        List<Result> results = resultRepository.findByStudentIdAndCourseId(studentId, courseId);
        return new StudentPerformanceDto(studentId, "Data unavailable", results, 0.0, "Attendance data currently unavailable");
    }
}

package com.educommerce.attendance.service;

import com.educommerce.attendance.client.StudentClient;
import com.educommerce.attendance.dto.AttendanceRequest;
import com.educommerce.attendance.dto.AttendanceSummary;
import com.educommerce.attendance.entity.Attendance;
import com.educommerce.attendance.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentClient studentClient;

    public Attendance markAttendance(AttendanceRequest request) {
        // Verify student exists via Feign call to Student Service
        try {
            studentClient.getStudentById(request.getStudentId());
        } catch (Exception e) {
            throw new RuntimeException("Student not found with id: " + request.getStudentId());
        }

        Attendance attendance = new Attendance();
        attendance.setStudentId(request.getStudentId());
        attendance.setCourseId(request.getCourseId());
        attendance.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
        attendance.setStatus(request.getStatus());

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public List<Attendance> getAttendanceByCourse(Long courseId) {
        return attendanceRepository.findByCourseId(courseId);
    }

    public Attendance updateAttendance(Long id, AttendanceRequest request) {
        Attendance existing = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found with id: " + id));
        existing.setStatus(request.getStatus());
        if (request.getDate() != null) existing.setDate(request.getDate());
        return attendanceRepository.save(existing);
    }

    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance record not found with id: " + id);
        }
        attendanceRepository.deleteById(id);
    }

    public AttendanceSummary getAttendanceSummary(Long studentId, Long courseId) {
        List<Attendance> records = attendanceRepository.findByStudentIdAndCourseId(studentId, courseId);
        long total = records.size();
        long present = records.stream()
                .filter(a -> a.getStatus() == Attendance.AttendanceStatus.PRESENT)
                .count();
        double percentage = total > 0 ? (present * 100.0) / total : 0.0;
        return new AttendanceSummary(studentId, courseId, total, present, percentage);
    }
}

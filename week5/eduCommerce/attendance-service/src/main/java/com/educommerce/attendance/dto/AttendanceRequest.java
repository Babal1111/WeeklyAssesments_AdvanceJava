package com.educommerce.attendance.dto;

import com.educommerce.attendance.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequest {
    private Long studentId;
    private Long courseId;
    private LocalDate date;
    private Attendance.AttendanceStatus status;
}

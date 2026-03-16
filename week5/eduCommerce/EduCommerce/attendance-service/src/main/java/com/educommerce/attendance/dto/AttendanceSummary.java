package com.educommerce.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSummary {
    private Long studentId;
    private Long courseId;
    private long totalClasses;
    private long presentCount;
    private double attendancePercentage;
}

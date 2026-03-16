package com.educommerce.result.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "attendance-service")
public interface AttendanceClient {

    @GetMapping("/attendance/summary/student/{studentId}/course/{courseId}")
    AttendanceSummary getAttendanceSummary(@PathVariable("studentId") Long studentId,
                                           @PathVariable("courseId") Long courseId);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class AttendanceSummary {
        private Long studentId;
        private Long courseId;
        private long totalClasses;
        private long presentCount;
        private double attendancePercentage;
    }
}

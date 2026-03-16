package com.educommerce.result.dto;

import com.educommerce.result.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPerformanceDto {
    private Long studentId;
    private String studentName;
    private List<Result> results;
    private double averagePercentage;
    private String attendanceSummary;
}

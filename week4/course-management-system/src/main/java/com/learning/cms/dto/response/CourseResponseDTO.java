package com.learning.cms.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CourseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer duration;
    private String level;
    private String instructorName;
    private LocalDateTime createdAt;
}

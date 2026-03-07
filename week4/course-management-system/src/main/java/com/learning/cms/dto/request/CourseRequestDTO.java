package com.learning.cms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @Positive(message = "Price must be positive")
    private Double price;

    @Positive(message = "Duration must be positive")
    private Integer duration;

    private String level;

    @NotNull(message = "Instructor ID is required")
    private Long instructorId;
}

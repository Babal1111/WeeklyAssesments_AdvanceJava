package com.educommerce.result.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private Long courseId;

    private String examType;

    private Double marksObtained;

    private Double maxMarks;

    private String grade;

    @PostLoad
    @PrePersist
    @PreUpdate
    public void calculateGrade() {
        if (marksObtained != null && maxMarks != null && maxMarks > 0) {
            double percentage = (marksObtained / maxMarks) * 100;
            if (percentage >= 90) grade = "A+";
            else if (percentage >= 80) grade = "A";
            else if (percentage >= 70) grade = "B";
            else if (percentage >= 60) grade = "C";
            else if (percentage >= 50) grade = "D";
            else grade = "F";
        }
    }
}

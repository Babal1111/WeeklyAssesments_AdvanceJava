package com.example.DoctorManagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {
    private String name;
    private String specialization;
    private int experience;
    private String hospitalName;
    private String availability;
}

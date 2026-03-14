package com.example.AppointmentManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private Long doctorId;
    private String name;
    private String specialization;
    private int experience;
    private String hospitalName;
    private String availability;
}

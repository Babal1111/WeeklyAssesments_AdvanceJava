package com.example.AppointmentManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long id;
    private String patientName;
    private Long doctorId;
    private LocalDate appointmentDate;
    private String status;
}

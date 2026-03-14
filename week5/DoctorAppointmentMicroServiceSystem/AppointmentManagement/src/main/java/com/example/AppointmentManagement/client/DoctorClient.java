package com.example.AppointmentManagement.client;

import com.example.AppointmentManagement.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DOCTORMANAGEMENT-SERVICE")
public interface DoctorClient {
    @GetMapping("/api/doctors/getDoctorById/{id}")
    public DoctorResponse getDoctorById(@PathVariable("id") Long id);
}

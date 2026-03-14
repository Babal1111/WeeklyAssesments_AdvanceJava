package com.example.AppointmentManagement.controller;

import com.example.AppointmentManagement.dto.AppointmentRequest;
import com.example.AppointmentManagement.dto.AppointmentResponse;
import com.example.AppointmentManagement.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentControler {
    public final AppointmentService appointmentService;

    @PostMapping("/createAppointment")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRequest appointmentRequest){
        AppointmentResponse appointmentResponse=appointmentService.createAppointment(appointmentRequest);
        return ResponseEntity.ok().body("made an appointment for "+appointmentResponse.getPatientName()+" with doctor");
    }

    @GetMapping("/GetAllAppoinments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(){
        List<AppointmentResponse> appointmentResponseList=appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointmentResponseList);
    }

    @GetMapping("/GetAppointmentById/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable("id") Long id){
        AppointmentResponse appointmentResponse=appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointmentResponse);
    }

    @DeleteMapping("/DeleteAppointment/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("id") Long id){
        AppointmentResponse appointmentResponse=appointmentService.deleteAppointment(id);
        return ResponseEntity.ok().body("appointment of "+appointmentResponse.getPatientName()+" is deleted");
    }
}

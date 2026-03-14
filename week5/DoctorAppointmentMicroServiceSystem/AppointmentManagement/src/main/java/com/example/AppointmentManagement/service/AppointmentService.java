package com.example.AppointmentManagement.service;

import com.example.AppointmentManagement.client.DoctorClient;
import com.example.AppointmentManagement.dto.AppointmentRequest;
import com.example.AppointmentManagement.dto.AppointmentResponse;
import com.example.AppointmentManagement.dto.DoctorResponse;
import com.example.AppointmentManagement.entity.Appointment;
import com.example.AppointmentManagement.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    public final AppointmentRepository appointmentRepository;
    public final ModelMapper modelMapper;
    private final DoctorClient doctorClient;

    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest){
        DoctorResponse doctor=doctorClient.getDoctorById(appointmentRequest.getDoctorId());
        if(doctor==null){
            throw new RuntimeException("Doctor not found");
        }
        Appointment appointment = new Appointment();
        appointment.setPatientName(appointmentRequest.getPatientName());
        appointment.setDoctorId(appointmentRequest.getDoctorId());
        appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        appointment.setStatus("SCHEDULED");
        Appointment savedAppointment = appointmentRepository.save(appointment);
        AppointmentResponse response = modelMapper.map(savedAppointment, AppointmentResponse.class);
        response.setId(savedAppointment.getId());
        return response;
    }

    public List<AppointmentResponse> getAllAppointments(){
        List<Appointment> appointmentList=appointmentRepository.findAll();
        List<AppointmentResponse> appointmentResponseList=appointmentList.stream().map(i->modelMapper.map(i, AppointmentResponse.class)).toList();
        return appointmentResponseList;
    }

    public AppointmentResponse getAppointmentById(Long id){
        Appointment appointment=appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Appointment not found"));
        AppointmentResponse appointmentResponse=modelMapper.map(appointment, AppointmentResponse.class);
        return appointmentResponse;
    }

    public AppointmentResponse deleteAppointment(Long id){
        Appointment appointment=appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("appointment not found"));
        appointmentRepository.delete(appointment);
        return modelMapper.map(appointment, AppointmentResponse.class);
    }
}

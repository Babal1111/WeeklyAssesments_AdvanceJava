package com.example.DoctorManagement.service;

import com.example.DoctorManagement.dto.DoctorRequest;
import com.example.DoctorManagement.dto.DoctorResponse;
import com.example.DoctorManagement.entity.Doctor;
import com.example.DoctorManagement.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import javax.print.Doc;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    public final DoctorRepository doctorRepository;
    public final ModelMapper modelMapper;

    public DoctorResponse addDoctor(DoctorRequest doctorRequest){
        Doctor doctor=modelMapper.map(doctorRequest, Doctor.class);
        Doctor savedDoctor=doctorRepository.save(doctor);
        DoctorResponse doctorResponse=modelMapper.map(savedDoctor, DoctorResponse.class);
        return doctorResponse;
    }

    public List<DoctorResponse> getAllDoctors(){
        List<Doctor> doctors=doctorRepository.findAll();
        List<DoctorResponse> doctorResponseList=doctors.stream().map(i->modelMapper.map(i, DoctorResponse.class)).toList();
        return doctorResponseList;
    }

    public DoctorResponse getDoctorById(Long id){
        Doctor doctor=doctorRepository.findById(id).orElseThrow(()->new RuntimeException("Doctor not found"));
        DoctorResponse doctorResponse=modelMapper.map(doctor, DoctorResponse.class);
        return doctorResponse;
    }

    public DoctorResponse updateDoctor(Long id, DoctorRequest doctorRequest){
        Doctor doctor=doctorRepository.findById(id).orElseThrow(()->new RuntimeException("doctor not found"));
        doctor.setName(doctorRequest.getName());
        doctor.setHospitalName(doctorRequest.getHospitalName());
        doctor.setAvailability(doctorRequest.getAvailability());
        doctor.setExperience(doctorRequest.getExperience());
        doctor.setSpecialization(doctorRequest.getSpecialization());
        doctorRepository.save(doctor);
        DoctorResponse doctorResponse=modelMapper.map(doctor, DoctorResponse.class);
        return doctorResponse;
    }

    public DoctorResponse deleteDoctor(Long id){
        Doctor doctor=doctorRepository.findById(id).orElseThrow(()->new RuntimeException("doctor not found"));
        doctorRepository.delete(doctor);
        DoctorResponse doctorResponse=modelMapper.map(doctor, DoctorResponse.class);
        return doctorResponse;
    }

}

package com.example.DoctorManagement.controller;

import com.example.DoctorManagement.dto.DoctorRequest;
import com.example.DoctorManagement.dto.DoctorResponse;
import com.example.DoctorManagement.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {
    public final DoctorService doctorService;

    @PostMapping("/addDoctor")
    public ResponseEntity<String> addDoctor(@RequestBody DoctorRequest doctorRequest){
        DoctorResponse doctorResponse=doctorService.addDoctor(doctorRequest);
        return ResponseEntity.ok().body(doctorResponse.getName()+" is added");
    }

    @GetMapping("/getAllDoctors")
    public ResponseEntity<List<DoctorResponse>> getAll(){
        List<DoctorResponse> doctorResponseList=doctorService.getAllDoctors();
        return ResponseEntity.ok(doctorResponseList);
    }

    @GetMapping("/getDoctorById/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable("id") Long id){
        DoctorResponse doctorResponse=doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctorResponse);
    }

    @PutMapping("/updateDoctor/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable("id") Long id, @RequestBody DoctorRequest doctorRequest){
        DoctorResponse doctorResponse=doctorService.updateDoctor(id, doctorRequest);
        return ResponseEntity.ok().body(doctorResponse.getName()+" is updated");
    }

    @DeleteMapping("deleteDoctor/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable("id") Long id){
        DoctorResponse doctorResponse=doctorService.deleteDoctor(id);
        return ResponseEntity.ok().body(doctorResponse.getName()+" is deleted");
    }
}

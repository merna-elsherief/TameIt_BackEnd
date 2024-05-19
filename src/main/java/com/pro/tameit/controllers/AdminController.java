package com.pro.tameit.controllers;

import com.pro.tameit.dto.ClinicDTO;
import com.pro.tameit.dto.SpecializationDTO;
import com.pro.tameit.dto.request.DoctorRequest;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final ClinicService clinicService;
    private final SpecializationService specializationService;
    //get all doctors
    @GetMapping("/getAllDoctors")
    public ResponseEntity<?> getAllDoctors(){
        try {
            return ResponseEntity.ok(doctorService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //get all users
    @GetMapping("/getAllPatients")
    public ResponseEntity<?> getAllPatients(){
        try {
            return ResponseEntity.ok(patientService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //get all Clinics
    @GetMapping("/getAllClinics")
    public ResponseEntity<?> getAllClinics(){
        try {
            return ResponseEntity.ok(clinicService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //get all Specialization
    @GetMapping("/getAllSpecializations")
    public ResponseEntity<?> getAllSpecializations(){
        try {
            return ResponseEntity.ok(specializationService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //Create Doctor with his Clinics and his Specialization
    @PostMapping("/addDoctor")
    public ResponseEntity<?> addDoctor(@RequestBody DoctorRequest doctorRequest){
        return new ResponseEntity<>(doctorService.addDoctor(doctorRequest),HttpStatus.CREATED);
    }
    //Create Specialization
    @PostMapping("/addSpecialization")
    public ResponseEntity<?> addSpecialization(@RequestBody SpecializationDTO specializationDTO){
        return new ResponseEntity<>(specializationService.addSpecialization(specializationDTO),HttpStatus.CREATED);
    }
    //Create Clinic
    @PostMapping("/addClinic")
    public ResponseEntity<?> addClinic(@RequestBody ClinicDTO clinicDTO){
        return new ResponseEntity<>(clinicService.addClinic(clinicDTO),HttpStatus.CREATED);
    }
    //update doctor

    //delete doctor

    //delete user

}

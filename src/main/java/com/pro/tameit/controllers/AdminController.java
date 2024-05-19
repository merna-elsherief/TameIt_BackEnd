package com.pro.tameit.controllers;

import com.pro.tameit.dto.request.DoctorRequest;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.services.DoctorService;
import com.pro.tameit.services.PatientService;
import com.pro.tameit.services.UserService;
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
    public ResponseEntity<?> getAllPatient(){
        try {
            return ResponseEntity.ok(patientService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //add doctor

    //request user password 123
    // chek specialization there exit or not if not we need add specialization
    // chek clinic there exit or not if not we need to add clinic

    @PostMapping
    public ResponseEntity<?> addDoctor(@RequestBody DoctorRequest doctorRequest){
        doctorService.addDoctor(doctorRequest);
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    //update doctor

    //delete doctor

    //delete user

}

package com.pro.tameit.controllers;

import com.pro.tameit.models.Doctor;
import com.pro.tameit.services.DoctorService;
import com.pro.tameit.services.PatientService;
import com.pro.tameit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DoctorService doctorService;
    private final PatientService patientService;

    //get all doctors
    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors(){
        List<String> doctorList = doctorService.getAll();
        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    //get all users
    @GetMapping("/patients")
    public ResponseEntity<?> getAllPatient(){
        List<String> patientList = patientService.getAll();
        return new ResponseEntity<>(patientList, HttpStatus.OK);
    }

    //add doctor

    //request user password 123
    // chek specialization there exit or not if not we need add specialization
    // chek clinic there exit or not if not we need to add clinic


    //update doctor

    //delete doctor

    //delete user

}

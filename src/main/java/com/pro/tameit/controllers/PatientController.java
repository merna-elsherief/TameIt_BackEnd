package com.pro.tameit.controllers;

import com.pro.tameit.dto.request.PatientRequest;
import com.pro.tameit.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;
    @GetMapping("/getPatientDetails")
    public ResponseEntity<?> getPatientDetails() {
        try {
            return ResponseEntity.ok(service.getPatientDetails());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping ("/editPatientDetails")
    public ResponseEntity<?> editPatientDetails(@RequestBody PatientRequest request) {
        try {
            return ResponseEntity.ok(service.editPatient(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

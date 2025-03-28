package com.pro.tameit.controllers;

import com.pro.tameit.dao.PatientSearchRequest;
import com.pro.tameit.dto.request.PatientRequest;
import com.pro.tameit.dto.response.PatientResponse;
import com.pro.tameit.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
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
    @GetMapping("/myDoctors")
    public ResponseEntity<?> myDoctors() {
        try {
            return ResponseEntity.ok(service.getMyDoctors());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/byName")
    public ResponseEntity<?> searchPatients(@RequestBody PatientSearchRequest query) {
        try {
            List<PatientResponse> patientResponseList = service.searchPatients(query);
            if (patientResponseList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Patients found for the given search term");
            } else {
                return new ResponseEntity<>(patientResponseList, HttpStatus.OK);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Search failed: " + e.getMessage());
        }
    }
}

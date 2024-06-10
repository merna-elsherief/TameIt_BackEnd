package com.pro.tameit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pro.tameit.dto.ClinicDTO;
import com.pro.tameit.dto.SpecializationDTO;
import com.pro.tameit.dto.request.DoctorRequest;
import com.pro.tameit.dto.response.DoctorCardResponse;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final ClinicService clinicService;
    private final SpecializationService specializationService;

    //get all patients
    @GetMapping("/getAllPatients")
    public ResponseEntity<?> getAllPatients(){
        try {
            return new ResponseEntity<>(patientService.getAll(),HttpStatus.OK);
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
    public ResponseEntity<?> addDoctor(@RequestParam("file") MultipartFile file,
                                       @RequestParam("json") String jsonString){
        ObjectMapper objectMapper = new ObjectMapper();
        DoctorRequest doctorRequest;
        try {
            doctorRequest = objectMapper.readValue(jsonString, DoctorRequest.class);
            doctorRequest.setFile(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid JSON");
        }
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
    @PatchMapping("/editDoctor/{id}")
    public ResponseEntity<?> editDoctor(@PathVariable Long id, @RequestBody DoctorRequest doctorRequest){
        return new ResponseEntity<>(doctorService.updateDoctor(id, doctorRequest),HttpStatus.OK);
    }
    //delete doctor
    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //delete patient
    @DeleteMapping("/deletePatient/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

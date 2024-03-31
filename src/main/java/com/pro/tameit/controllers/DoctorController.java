package com.pro.tameit.controllers;

import com.pro.tameit.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllDoctors() {
        List<String> doctorList = doctorService.getAll();
        if(doctorList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No doctors found ");
        }
        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    @GetMapping("/byName")
    public ResponseEntity<?> searchDoctors(@RequestParam String searchTerm) {
        List<String> doctorList = doctorService.searchDoctors(searchTerm);
        if (doctorList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No doctors found for the given search term");
        } else {
            return new ResponseEntity<>(doctorList, HttpStatus.OK);
        }
    }

    @GetMapping("/sort")
    public ResponseEntity<?> sortDoctors(@RequestParam(required = false, defaultValue = "asc") String order) {
        List<String> sortedDoctorList = doctorService.sortDoctors(order);

        if (sortedDoctorList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No doctors found.");
        } else {
            return ResponseEntity.ok(sortedDoctorList);
        }
    }
}

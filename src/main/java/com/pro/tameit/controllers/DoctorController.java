package com.pro.tameit.controllers;

import com.pro.tameit.dto.response.DoctorCardResponse;
import com.pro.tameit.services.DoctorServiceImpl;
import com.pro.tameit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorServiceImpl doctorService;
    private final UserService userService;

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
    @GetMapping("/doctorCard/{userName}")
    public ResponseEntity<?> doctorCard(@PathVariable String userName) {
        try {
            DoctorCardResponse doctorCardResponse = userService.doctorCard(userName);
            return ResponseEntity.status(HttpStatus.OK).body(doctorCardResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("doctor doesn't found.");
        }
    }
}

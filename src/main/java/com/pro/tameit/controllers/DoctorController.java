package com.pro.tameit.controllers;

import com.pro.tameit.cloudinary.dto.ImageModel;
import com.pro.tameit.cloudinary.services.ImageService;
import com.pro.tameit.dao.DoctorSearchRequest;
import com.pro.tameit.dto.response.DoctorCardResponse;
import com.pro.tameit.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final ImageService imageService;

    @GetMapping("/auth/getAllDoctors")
    public ResponseEntity<?> getAllDoctors(){
        try {
            return ResponseEntity.ok(doctorService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/auth/byName")
    public ResponseEntity<?> searchDoctors(@RequestBody DoctorSearchRequest query) {
        try {
            List<DoctorCardResponse> doctorList = doctorService.searchDoctors(query);
            if (doctorList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No doctors found for the given search term");
            } else {
                return new ResponseEntity<>(doctorList, HttpStatus.OK);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Search failed: " + e.getMessage());
        }
    }

//    @GetMapping("/auth/sort")
//    public ResponseEntity<?> sortDoctors(@RequestParam(required = false, defaultValue = "asc") String order) {
//        try {
//            List<String> sortedDoctorList = doctorService.sortDoctors(order);
//
//            if (sortedDoctorList.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No doctors found.");
//            } else {
//                return ResponseEntity.ok(sortedDoctorList);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("sort failed: " + e.getMessage());
//        }
//    }
    @GetMapping("/auth/doctorCard/{id}")
    public ResponseEntity<?> doctorCard(@PathVariable Long id) {
        try {
            DoctorCardResponse doctorCardResponse = doctorService.findDoctorById(id);
            return ResponseEntity.status(HttpStatus.OK).body(doctorCardResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("doctor doesn't found.");
        }
    }
    @PatchMapping("/auth/changeUserImage/{id}")
    public ResponseEntity<?> changeDoctorImage(@PathVariable Long id, ImageModel imageModel) {
        try {
            return ResponseEntity.ok(imageService.changeUserImageByDoctorId(id, imageModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/doctor/myPatients")
    public ResponseEntity<?> myPatients() {
        try {
            return ResponseEntity.ok(doctorService.getMyPatients());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

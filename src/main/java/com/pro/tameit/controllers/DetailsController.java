package com.pro.tameit.controllers;

import com.pro.tameit.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DetailsController {
    private final AuthenticationService service;
    @PostMapping("/details")
    public ResponseEntity<?> details() {
        try {
            return ResponseEntity.ok(service.details());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
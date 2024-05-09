package com.pro.tameit.controllers;

import com.pro.tameit.services.AuthenticationService;
import com.pro.tameit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DetailsController {
    private final UserService service;
    @GetMapping("/auth/userDetails")
    public ResponseEntity<?> userDetails() {
        try {
            return ResponseEntity.ok(service.details());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

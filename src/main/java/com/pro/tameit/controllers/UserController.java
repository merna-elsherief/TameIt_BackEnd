package com.pro.tameit.controllers;

import com.pro.tameit.cloudinary.dto.ImageModel;
import com.pro.tameit.cloudinary.services.ImageService;
import com.pro.tameit.services.UserService;
import com.pro.tameit.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final ImageService imageService;
    @GetMapping("/details")
    public ResponseEntity<?> details() {
        try {
            return ResponseEntity.ok(service.details());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PatchMapping("/changeUserImage")
    public ResponseEntity<?> changeUserImage(ImageModel imageModel) {
        try {
            return ResponseEntity.ok(imageService.changeUserImage(imageModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

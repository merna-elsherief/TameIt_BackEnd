package com.pro.tameit.controllers;

import com.pro.tameit.dto.request.JwtRequest;
import com.pro.tameit.dto.response.JwtResponse;
import com.pro.tameit.services.AuthService;
import com.pro.tameit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class RegisterController {

    private final AuthService authService;
    @Autowired
    private UserService userService;


    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody JwtRequest request, BindingResult bindingResult) {
        try {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            if (!request.isPasswordMatching()) {
                errors.add("Password and Confirm Password must match");
            }

            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(errors);
            }

            JwtResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }


    @GetMapping("/auth/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String token){
        boolean verified = userService.verifyEmail(token);
        if (verified) {
            return ResponseEntity.ok("Email verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification token.");
        }
    }
}

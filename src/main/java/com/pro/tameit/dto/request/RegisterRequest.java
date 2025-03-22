package com.pro.tameit.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    private String userName;

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "password is required")
    private String confirmPassword;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "first name is required and can't be empty")
    @Size(min = 2, max = 30,message = "first name must be between 2 -> 30 characters")
    private String firstName;

    @NotBlank(message = "last name is required and can't be empty")
    @Size(min = 2, max = 30,message = "last name must be between 2 -> 30 characters")
    private String lastName;

    private String birthDate;

    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}

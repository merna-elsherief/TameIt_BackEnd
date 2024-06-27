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

    @Size(min = 3, message = "Username must be at least 3 characters long")
    @NotBlank(message = "Username is required")
    private String userName;
    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must be at least 6 characters long")
    private String password;
    @NotBlank(message = "password is required")
    private String confirmPassword;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;

    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}

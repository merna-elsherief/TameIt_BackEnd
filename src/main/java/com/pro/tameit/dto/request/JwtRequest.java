package com.pro.tameit.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

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


    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}

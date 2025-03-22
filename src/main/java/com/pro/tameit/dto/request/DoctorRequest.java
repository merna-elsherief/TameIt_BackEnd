package com.pro.tameit.dto.request;

import com.pro.tameit.domain.DoctorJobTitle;
import com.pro.tameit.domain.EGender;
import com.pro.tameit.models.Clinic;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequest {

    @NotBlank(message = "first name is required and can't be empty")
    @Size(min = 2, max = 30,message = "first name must be between 2 -> 30 characters")
    private String firstName;

    @NotBlank(message = "last name is required and can't be empty")
    @Size(min = 2, max = 30,message = "last name must be between 2 -> 30 characters")
    private String lastName;

    @NotBlank(message = "phone number must be entered")
    @Pattern(regexp = "^01[0-25]\\d{8}$\n", message = "this number is not an egyptian number")
    private String phoneNumber;

    @NotNull(message = "price must be entered")
    private Integer price;

    private Integer rating;

    private Integer yearsOfExperience;

    private DoctorJobTitle jobTitle;

    private EGender gender;

    private RegisterRequest registerRequest;

    private List<String> specializations;

    private List<Clinic> clinics;

    private String about;

    private MultipartFile file;
}

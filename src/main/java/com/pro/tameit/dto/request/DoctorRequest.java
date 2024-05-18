package com.pro.tameit.dto.request;

import com.pro.tameit.domain.DoctorJobTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer price;
    private Integer yearsOfExperience;
    private DoctorJobTitle jobTitle;

    private RegisterRequest registerRequest;
}

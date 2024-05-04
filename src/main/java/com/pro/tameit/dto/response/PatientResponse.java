package com.pro.tameit.dto.response;

import com.pro.tameit.dto.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private EGender gender;
    private String city;
    private String country;
    private String birthDate;
}

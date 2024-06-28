package com.pro.tameit.dto.response;

import com.pro.tameit.domain.DoctorJobTitle;
import com.pro.tameit.domain.EGender;
import com.pro.tameit.models.Clinic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer price;
    private Integer yearsOfExperience;
    private DoctorJobTitle jobTitle;
    private EGender gender;
    private List<String> specializations;
    private List<Clinic> clinics;
    private String about;
}

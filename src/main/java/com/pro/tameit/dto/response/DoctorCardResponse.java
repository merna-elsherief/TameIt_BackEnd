package com.pro.tameit.dto.response;

import com.pro.tameit.domain.DoctorJobTitle;
import com.pro.tameit.dto.ClinicDTO;
import com.pro.tameit.dto.SpecializationDTO;
import com.pro.tameit.models.Clinic;
import com.pro.tameit.models.Specialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorCardResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private Integer price;
    private String phoneNumber;
    private Integer rating;
    private Integer yearsOfExperience;
    private DoctorJobTitle jobTitle;
    private List<SpecializationDTO> specializations;
    private List<ClinicDTO> clinics;
    private String about;
}

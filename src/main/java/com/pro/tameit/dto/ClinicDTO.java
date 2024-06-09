package com.pro.tameit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicDTO {
    private String clinicName;
    private String address;
    private String phoneNumber;
}

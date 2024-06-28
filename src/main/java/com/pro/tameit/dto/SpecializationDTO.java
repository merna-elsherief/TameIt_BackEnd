package com.pro.tameit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SpecializationDTO {
    private final String specializationName;
}

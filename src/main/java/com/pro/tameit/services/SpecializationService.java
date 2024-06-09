package com.pro.tameit.services;

import com.pro.tameit.dto.SpecializationDTO;

import java.util.List;

public interface SpecializationService {
    List<String> getAll();

    SpecializationDTO addSpecialization(SpecializationDTO specializationDTO);
}

package com.pro.tameit.services;

import com.pro.tameit.dto.ClinicDTO;
import com.pro.tameit.models.Clinic;

import java.util.List;

public interface ClinicService {
    List<ClinicDTO> getAll();

    ClinicDTO addClinic(ClinicDTO clinicDTO);

    void deleteClinic(Long id);

    ClinicDTO mapToClinicDTO(Clinic clinic);
}

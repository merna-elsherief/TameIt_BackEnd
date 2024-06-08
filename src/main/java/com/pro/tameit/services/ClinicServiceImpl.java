package com.pro.tameit.services;

import com.pro.tameit.dto.ClinicDTO;
import com.pro.tameit.models.Clinic;
import com.pro.tameit.repo.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService{
    private final ClinicRepository clinicRepository;
    @Override
    public List<ClinicDTO> getAll() {
        return clinicRepository.findAll().stream()
                .map(this::mapToClinicDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClinicDTO addClinic(ClinicDTO clinicDTO) {
        Clinic returnedClinic = clinicRepository.findByClinicNameContainsIgnoreCase(clinicDTO.getClinicName());
        if (returnedClinic == null){
            Clinic clinicBuilder = Clinic.builder().clinicName(clinicDTO.getClinicName()).address(clinicDTO.getAddress()).phoneNumber(clinicDTO.getPhoneNumber()).build();
            clinicRepository.save(clinicBuilder);
        }
        return clinicDTO;
    }
    @Override
    public void deleteClinic(Long id) {
        clinicRepository.deleteById(id);
    }

    @Override
    public ClinicDTO mapToClinicDTO(Clinic clinic){
        ClinicDTO dto = new ClinicDTO();
        dto.setClinicName(clinic.getClinicName());
        dto.setAddress(clinic.getAddress());
        dto.setPhoneNumber(clinic.getPhoneNumber());
        return dto;
    }
}

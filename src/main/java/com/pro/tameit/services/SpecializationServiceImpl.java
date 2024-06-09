package com.pro.tameit.services;

import com.pro.tameit.dto.SpecializationDTO;
import com.pro.tameit.models.Specialization;
import com.pro.tameit.repo.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {
    private final SpecializationRepository specializationRepository;
    @Override
    public List<String> getAll() {
        return specializationRepository.findAll().stream()
                .map(Specialization::getSpecializationName)
                .collect(Collectors.toList());
    }

    @Override
    public SpecializationDTO addSpecialization(SpecializationDTO specializationDTO) {
        Specialization returnedSpecialization = specializationRepository.findBySpecializationName(specializationDTO.getSpecializationName());
        if (returnedSpecialization==null){
            Specialization specialization = Specialization.builder().specializationName(specializationDTO.getSpecializationName()).build();
            specializationRepository.save(specialization);
        }
        return specializationDTO;
    }

}

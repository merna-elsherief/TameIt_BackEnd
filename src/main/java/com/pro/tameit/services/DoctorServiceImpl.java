package com.pro.tameit.services;

import com.pro.tameit.models.Doctor;
import com.pro.tameit.repo.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public List<String> getAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(Doctor::getFirstName)
                .collect(Collectors.toList());
    }
    @Override
    public List<String> searchDoctors(String searchTerm) {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .filter(doctor -> doctor.getFirstName().contains(searchTerm))
                .map(Doctor::getFirstName)
                .collect(Collectors.toList());
    }
    @Override
    public List<String> sortDoctors(String order) {
        List<Doctor> doctors = doctorRepository.findAll();

        if (order.equalsIgnoreCase("asc")) {
            return doctors.stream()
                    .map(Doctor::getFirstName)
                    .sorted()
                    .collect(Collectors.toList());
        } else if (order.equalsIgnoreCase("desc")) {
            return doctors.stream()
                    .map(Doctor::getFirstName)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
        } else {
            // Invalid order, return empty list
            return List.of();
        }
    }
}

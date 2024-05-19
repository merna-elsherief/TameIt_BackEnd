package com.pro.tameit.services;

import com.pro.tameit.domain.ERole;
import com.pro.tameit.dto.request.DoctorRequest;
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
    private final AuthenticationService authenticationService;

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
    @Override
    public String addDoctor(DoctorRequest doctorRequest) {
        //add Doctor To USER DB & DOCTOR DB
        authenticationService.register(doctorRequest.getRegisterRequest(), ERole.DOCTOR);
        //add Doctor details:
        //first get l doctor with the userName
        Doctor doctor = doctorRepository.findByUserName(doctorRequest.getRegisterRequest().getUserName()).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        //Then add doctor details:
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        doctor.setPhoneNumber(doctorRequest.getPhoneNumber());
        doctor.setPrice(doctorRequest.getPrice());
        doctor.setYearsOfExperience(doctorRequest.getYearsOfExperience());
        doctor.setJobTitle(doctorRequest.getJobTitle());
        doctorRepository.save(doctor);
        return "";
    }
}

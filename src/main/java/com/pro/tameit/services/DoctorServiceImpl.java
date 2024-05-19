package com.pro.tameit.services;

import com.pro.tameit.domain.ERole;
import com.pro.tameit.dto.request.DoctorRequest;
import com.pro.tameit.models.Clinic;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Specialization;
import com.pro.tameit.repo.ClinicRepository;
import com.pro.tameit.repo.DoctorRepository;
import com.pro.tameit.repo.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final AuthenticationService authenticationService;
    private final SpecializationRepository specializationRepository;
    private final ClinicRepository clinicRepository;
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
        doctor.setGender(doctorRequest.getGender());
        //Specialization:
        //check If specialization exist or not:
        List<String> doctorRequestSpecializations = doctorRequest.getSpecializations();
        if (doctorRequestSpecializations!=null){
            for (String specializationName:
                 doctorRequestSpecializations) {
                Specialization returnedSpecialization = specializationRepository.findBySpecializationName(specializationName);
                if (returnedSpecialization == null){
                    //h n addoh b2a
                    //h n check if ana asln 3ndy fe specilizations ll doctor da wla l2a:
                    Specialization specialization = Specialization.builder().specializationName(specializationName).build();
                    specializationRepository.save(specialization);
                    if (doctor.getSpecializations()==null){
                        doctor.setSpecializations(new ArrayList<>());
                    }
                    doctor.getSpecializations().add(specialization);
                }else {
                    if (doctor.getSpecializations()==null){
                        doctor.setSpecializations(new ArrayList<>());
                    }
                    doctor.getSpecializations().add(returnedSpecialization);
                }
            }
        }
        //add l clinics b2a:
        List<Clinic> doctorRequestClinics = doctorRequest.getClinics();
        if (doctorRequestClinics!=null){
            for (Clinic clinic:
                 doctorRequestClinics) {
                Clinic returnedClinic = clinicRepository.findByClinicNameContainsIgnoreCase(clinic.getClinicName());
                if (returnedClinic == null){
                    Clinic clinicBuilder = Clinic.builder().clinicName(clinic.getClinicName()).address(clinic.getAddress()).phoneNumber(clinic.getPhoneNumber()).build();
                    clinicRepository.save(clinicBuilder);
                    if (doctor.getClinics()==null){
                        doctor.setClinics(new ArrayList<>());
                    }
                    doctor.getClinics().add(clinicBuilder);
                }
            }
        }
        doctorRepository.save(doctor);
        return "";
    }
}

package com.pro.tameit.services;

import com.pro.tameit.dto.SpecializationDTO;
import com.pro.tameit.dto.response.DoctorCardResponse;
import com.pro.tameit.dto.response.PatientResponse;
import com.pro.tameit.models.Clinic;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Patient;
import com.pro.tameit.models.Specialization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedServicesImpl implements SharedServices{
    private final ClinicService clinicService;
    @Override
    public DoctorCardResponse mapToDoctorCardResponse(Doctor doctor) {
        DoctorCardResponse doctorCardResponse = new DoctorCardResponse();

        doctorCardResponse.setId(doctor.getId());
        doctorCardResponse.setFirstName(doctor.getFirstName());
        doctorCardResponse.setLastName(doctor.getLastName());

        // Handle the image URL, check if the image is not null
        if (doctor.getUser() != null && doctor.getUser().getImage() != null) {
            doctorCardResponse.setImageUrl(doctor.getUser().getImage().getUrl());
        } else {
            doctorCardResponse.setImageUrl(null);
        }
        doctorCardResponse.setRating(doctor.getRating());
        doctorCardResponse.setPrice(doctor.getPrice());
        doctorCardResponse.setPhoneNumber(doctor.getPhoneNumber());
        doctorCardResponse.setYearsOfExperience(doctor.getYearsOfExperience());
        doctorCardResponse.setJobTitle(doctor.getJobTitle());
        //Specialization Mapping:
        List<Specialization> specializationList = doctor.getSpecializations();
        doctorCardResponse.setSpecializations(new ArrayList<>());
        for (Specialization s:
                specializationList) {
            doctorCardResponse.getSpecializations().add(new SpecializationDTO(s.getSpecializationName()));
        }
        //Clinic Mapping
        List<Clinic> clinicList = doctor.getClinics();
        doctorCardResponse.setClinics(new ArrayList<>());
        for (Clinic clinic:
                clinicList) {
            doctorCardResponse.getClinics().add(clinicService.mapToClinicDTO(clinic));
        }
        doctorCardResponse.setAbout(doctor.getAbout());
        return doctorCardResponse;
    }
    @Override
    public PatientResponse mapToPatientResponse(Patient patient) {
        PatientResponse patientResponse = new PatientResponse();

        patientResponse.setId(patient.getId());
        patientResponse.setFirstName(patient.getFirstName());
        patientResponse.setLastName(patient.getLastName());
        patientResponse.setEmail(patient.getUser().getEmail());
        // Handle the image URL, check if the image is not null
        if (patient.getUser() != null && patient.getUser().getImage() != null) {
            patientResponse.setImageUrl(patient.getUser().getImage().getUrl());
        } else {
            patientResponse.setImageUrl(null);
        }
        patientResponse.setPhoneNumber(patient.getPhoneNumber());
        patientResponse.setCity(patient.getCity());
        patientResponse.setCountry(patient.getCountry());
        SimpleDateFormat dateFormatter = new SimpleDateFormat ("dd-MM-yyyy");
        patientResponse.setBirthDate(dateFormatter.format(patient.getBirthDate()));
        patientResponse.setGender(patient.getGender());
        return patientResponse;
    }
}

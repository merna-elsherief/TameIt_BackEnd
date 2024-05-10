package com.pro.tameit.services;

import com.pro.tameit.dto.request.PatientRequest;
import com.pro.tameit.dto.response.PatientResponse;
import com.pro.tameit.models.Patient;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.PatientRepository;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService{
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    @Override
    public Patient editPatient(PatientRequest request) throws ParseException {
        //First get the user from our SecurityContextHolder
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //Second get the patient
        Patient patient = patientRepository.findByUserId(userName).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        //Last h n edit om l patient b2a
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setCity(request.getCity());
        patient.setCountry(request.getCountry());
        //patient.setGender(request.getGender());
        //edit of BirthDate need more actions
        SimpleDateFormat dateParser = new SimpleDateFormat ("dd/MM/yyyy"); //Format for input
        String date = request.getBirthDate();
        Date dn = dateParser.parse(date); //Parsing the date

         //Format for output

        patient.setBirthDate(dn);
        patientRepository.save(patient);
        return patient;
    }
    @Override
    public PatientResponse getPatientDetails(){
        PatientResponse patientResponse = new PatientResponse();
        //First get the user from our SecurityContextHolder
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow();
        //Second get the patient
        Patient patient = patientRepository.findByUserId(userName).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        patientResponse.setFirstName(patient.getFirstName());
        patientResponse.setLastName(patient.getLastName());
        patientResponse.setEmail(user.getEmail());
        patientResponse.setPhoneNumber(patient.getPhoneNumber());
        patientResponse.setCity(patient.getCity());
        patientResponse.setCountry(patient.getCountry());
        SimpleDateFormat dateFormatter = new SimpleDateFormat ("dd-MM-yyyy");
        patientResponse.setBirthDate(dateFormatter.format(patient.getBirthDate()));
        patientResponse.setGender(patient.getGender());
        return patientResponse;
    }
}

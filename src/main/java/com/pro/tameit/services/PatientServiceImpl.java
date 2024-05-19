package com.pro.tameit.services;

import com.pro.tameit.dto.request.PatientRequest;
import com.pro.tameit.dto.response.PatientResponse;
import com.pro.tameit.models.Patient;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.PatientRepository;
import com.pro.tameit.repo.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        if (request.getFirstName()!=null){
            patient.setFirstName(request.getFirstName());
        }
        if (request.getLastName()!=null){
            patient.setLastName(request.getLastName());
        }
        if (request.getPhoneNumber()!=null){
            patient.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getCity()!=null){
            patient.setCity(request.getCity());
        }
        if (request.getCountry()!=null){
            patient.setCountry(request.getCountry());
        }
        if (request.getGender()!=null){
            patient.setGender(request.getGender());
        }
        if (request.getBirthDate()!=null){//edit of BirthDate need more actions
            SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy"); //Format for input
            String date = request.getBirthDate();
            Date dn = dateParser.parse(date); //Parsing the date

            //Format for output

            patient.setBirthDate(dn);
        }
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
    @Override
    public List<String> getAll() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(Patient::getFirstName)
                .collect(Collectors.toList());
    }
}

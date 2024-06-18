package com.pro.tameit.services;

import com.pro.tameit.dao.PatientSearchDao;
import com.pro.tameit.dao.PatientSearchRequest;
import com.pro.tameit.dto.request.PatientRequest;
import com.pro.tameit.dto.response.DoctorCardResponse;
import com.pro.tameit.dto.response.PatientResponse;
import com.pro.tameit.models.*;
import com.pro.tameit.repo.PatientRepository;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService{
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final AppointmentService appointmentService;
    private final SharedServicesImpl sharedServices;
    private final PatientSearchDao patientSearchDao;
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
        //First get the user from our SecurityContextHolder
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //Second get the patient
        Patient patient = patientRepository.findByUserId(userName).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        return mapToPatientResponse(patient);
    }
    @Override
    public List<PatientResponse> getAll() {
        return patientRepository.findAll().stream()
                .map(this::mapToPatientResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<PatientResponse> searchPatients(PatientSearchRequest query) {
        List<Patient> patients = patientSearchDao.findAllByCriteria(query);
        return patients.stream()
                .map(this::mapToPatientResponse)
                .collect(Collectors.toList());
    }
    @Override
    public void deletePatient(Long id){
        Patient patient = patientRepository.findUserBPatientId(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        appointmentService.deletePatientFromAppointmentsById(id);
        userRepository.deleteById(patient.getUser().getId());
        patientRepository.deleteById(id);
    }
    @Override
    public List<DoctorCardResponse> getMyDoctors() {
        //First get the user from our SecurityContextHolder
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //Second get the patient
        Patient patient = patientRepository.findByUserId(userName).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        List<Doctor> doctors = patientRepository.findMyDoctors(patient.getId()).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        if (doctors.isEmpty()){
            return null;
        } else {
            List<DoctorCardResponse> responseList = new ArrayList<>();
            for (Doctor d:
                 doctors) {
                responseList.add(sharedServices.mapToDoctorCardResponse(d));
            }
            return responseList;
        }

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

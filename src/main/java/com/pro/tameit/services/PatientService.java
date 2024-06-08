package com.pro.tameit.services;

import com.pro.tameit.dto.request.PatientRequest;
import com.pro.tameit.dto.response.PatientResponse;
import com.pro.tameit.models.Patient;

import java.text.ParseException;
import java.util.List;

public interface PatientService {
    Patient editPatient(PatientRequest request) throws ParseException;

    PatientResponse getPatientDetails();

    List<String> getAll();

    void deletePatient(Long id);
}

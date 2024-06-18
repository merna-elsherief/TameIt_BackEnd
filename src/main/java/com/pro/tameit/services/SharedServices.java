package com.pro.tameit.services;

import com.pro.tameit.dto.response.DoctorCardResponse;
import com.pro.tameit.dto.response.PatientResponse;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Patient;

public interface SharedServices {
    DoctorCardResponse mapToDoctorCardResponse(Doctor doctor);

    PatientResponse mapToPatientResponse(Patient patient);
}

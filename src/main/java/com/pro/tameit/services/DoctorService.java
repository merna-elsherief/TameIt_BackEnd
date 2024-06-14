package com.pro.tameit.services;

import com.pro.tameit.dao.DoctorSearchRequest;
import com.pro.tameit.dto.request.DoctorRequest;
import com.pro.tameit.dto.response.DoctorCardResponse;
import com.pro.tameit.models.Doctor;

import java.util.List;

public interface DoctorService {


    List<DoctorCardResponse> getAll();


    List<DoctorCardResponse> searchDoctors(DoctorSearchRequest query);

    String addDoctor(DoctorRequest doctorRequest);

    DoctorCardResponse findDoctorById(Long id);

    void deleteDoctorById(Long id);

    String updateDoctor(Long id, DoctorRequest doctorRequest);

    DoctorCardResponse mapToDoctorCardResponse(Doctor doctor);
}

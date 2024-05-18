package com.pro.tameit.services;

import com.pro.tameit.dto.request.DoctorRequest;

import java.util.List;

public interface DoctorService {
    List<String> getAll();

    List<String> searchDoctors(String searchTerm);

    List<String> sortDoctors(String order);

    String addDoctor(DoctorRequest doctorRequest);
}

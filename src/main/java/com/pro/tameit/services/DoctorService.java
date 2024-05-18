package com.pro.tameit.services;

import java.util.List;

public interface DoctorService {
    List<String> getAll();

    List<String> searchDoctors(String searchTerm);

    List<String> sortDoctors(String order);

    String addDoctor(String order);
}

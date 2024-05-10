package com.pro.tameit.services;

import com.pro.tameit.dto.response.DetailsResponse;
import com.pro.tameit.dto.response.DoctorCardResponse;

public interface UserService {
    DetailsResponse details();

    boolean verifyEmail(String token);

    DoctorCardResponse doctorCard(String userName);
}

package com.pro.tameit.services;

import com.pro.tameit.dto.response.DetailsResponse;
import com.pro.tameit.dto.response.DoctorCardResponse;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.DoctorRepository;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public DetailsResponse details(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow();
        DetailsResponse detailsResponse = new DetailsResponse();
        detailsResponse.setUserName(user.getUsername());
        detailsResponse.setEmail(user.getEmail());
        if (user.getImage() == null){
            detailsResponse.setImageUrl("https://res-console.cloudinary.com/dhta0azvx/thumbnails/v1/image/upload/v1714768348/Zm9sZGVyXzEvaGZpcmVzeWxyNnlpcWJtcnBnajg=/preview");
        }else {
            detailsResponse.setImageUrl(user.getImage().getUrl());
        }
        return detailsResponse;
    }
    @Override
    public boolean verifyEmail(String token){
        User user = userRepository.findByVerificationToken(token).orElseThrow(()->new RuntimeException("Please provide an valid userName!"));
        if (user != null && !user.isEmailVerified()) {
            user.setEmailVerified(true);
            user.setVerificationToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    @Override
    public DoctorCardResponse doctorCard(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow();
        Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow();
        DoctorCardResponse doctorCardResponse = new DoctorCardResponse();
        doctorCardResponse.setPrice(doctor.getPrice());
        doctorCardResponse.setPhoneNumber(doctor.getPhoneNumber());
        doctorCardResponse.setFirstName(doctor.getFirstName());
        doctorCardResponse.setLastName(doctor.getLastName());
        doctorCardResponse.setRating(doctor.getRating());
        if (doctor.getUser().getImage() != null){
            doctorCardResponse.setImageUrl(doctor.getUser().getImage().getUrl());
        }else {
            doctorCardResponse.setImageUrl("https://res-console.cloudinary.com/dhta0azvx/thumbnails/v1/image/upload/v1714768348/Zm9sZGVyXzEvaGZpcmVzeWxyNnlpcWJtcnBnajg=/preview");
        }
        doctorCardResponse.setSpecializations(doctor.getSpecializations());
        doctorCardResponse.setJobTitle(doctor.getJobTitle());
        doctorCardResponse.setYearsOfExperience(doctor.getYearsOfExperience());
        return doctorCardResponse;
    }
}

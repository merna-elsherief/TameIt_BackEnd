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
    private final DoctorService doctorService;

    @Override
    public DetailsResponse details(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow();
        DetailsResponse detailsResponse = new DetailsResponse();
        detailsResponse.setUserName(user.getUsername());
        detailsResponse.setEmail(user.getEmail());
        if (user.getImage() == null){
            detailsResponse.setImageUrl(null);
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
        return doctorService.mapToDoctorCardResponse(doctor);
    }
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}

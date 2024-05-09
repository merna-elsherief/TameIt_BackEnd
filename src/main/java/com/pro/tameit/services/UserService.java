package com.pro.tameit.services;

import com.pro.tameit.cloudinary.dto.ImageModel;
import com.pro.tameit.cloudinary.dto.ImageResponse;
import com.pro.tameit.cloudinary.services.ImageService;
import com.pro.tameit.dto.response.DetailsResponse;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;

    public DetailsResponse details( ){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow();
        return DetailsResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .imageUrl(user.getImage().getUrl())
                .build();
    }
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
}

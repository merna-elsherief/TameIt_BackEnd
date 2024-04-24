package com.pro.tameit.services;

import com.pro.tameit.models.User;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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

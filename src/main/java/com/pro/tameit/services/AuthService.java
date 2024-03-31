package com.pro.tameit.services;

import com.pro.tameit.dto.request.JwtRequest;
import com.pro.tameit.dto.response.JwtResponse;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.UserRepository;
import com.pro.tameit.security.JwtTokenUtil;
import com.pro.tameit.security.UserDetailsImpl;
import com.pro.tameit.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.pro.tameit.util.VerificationTokenUtil.generateToken;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    @Autowired
    private EmailSenderService emailSenderService;


    public JwtResponse login(JwtRequest request) {

        final Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(
                request.getUserName());

        return new JwtResponse(jwtTokenUtil.addAuthentication(user));
    }

    public JwtResponse register(JwtRequest request) {
        User user;
        String token = generateToken();
        user = new User(null, request.getUserName(), passwordEncoder.encode(request.getPassword())
                , request.getConfirmPassword(), request.getEmail(), token, false);
        userRepo.save(user);
        System.out.println("verificationToken is =" + user.getVerificationToken());
        sendVerificationEmail(user);
        return login(request);
    }

    private void sendVerificationEmail(User user) {
        String verificationLink = "https://tameit.azurewebsites.net/api/auth/verify-email?token=" + user.getVerificationToken();
        System.out.println("Link is sent" + verificationLink);
        emailSenderService.sendVerificationEmail(user.getEmail(), "Verification Email",verificationLink);
    }

}


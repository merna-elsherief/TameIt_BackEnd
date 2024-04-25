package com.pro.tameit.services;

import com.pro.tameit.config.JwtService;
import com.pro.tameit.dto.request.AuthenticationRequest;
import com.pro.tameit.dto.request.RegisterRequest;
import com.pro.tameit.dto.response.AuthenticationResponse;
import com.pro.tameit.dto.response.DetailsResponse;
import com.pro.tameit.dto.ERole;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.pro.tameit.util.VerificationTokenUtil.generateToken;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;
    public AuthenticationResponse register(RegisterRequest request){
        String token = generateToken();
        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verificationToken(token)
                .role(ERole.USER)
                .build();
        userRepository.save(user);
        sendVerificationEmail(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public DetailsResponse details( ){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow();
        return DetailsResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .build();
    }
    //not best practice
        private void sendVerificationEmail(User user) {
        String verificationLink = "This your verification mail: \n You should Click This Link to be verified \n https://tameit.azurewebsites.net/api/auth/verify-email?token=" + user.getVerificationToken();
        log.info("Link is sent" + verificationLink);
        emailSenderService.sendVerificationEmail(user.getEmail(), "Verification Email",verificationLink);
    }
}
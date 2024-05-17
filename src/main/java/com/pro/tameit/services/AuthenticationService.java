package com.pro.tameit.services;

import com.pro.tameit.config.JwtService;
import com.pro.tameit.dto.request.AuthenticationRequest;
import com.pro.tameit.dto.request.RegisterRequest;
import com.pro.tameit.dto.response.AuthenticationResponse;
import com.pro.tameit.domain.ERole;
import com.pro.tameit.models.Patient;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.PatientRepository;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.pro.tameit.util.VerificationTokenUtil.generateToken;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final PatientRepository patientRepository;
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
                //Because he is registering
                .role(ERole.PATIENT)
                .build();
        userRepository.save(user);
        //Add User to Patient DB
        Patient patient = Patient.builder()
                .user(user)
                .build();
        patientRepository.save(patient);
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
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    //not best practice
        private void sendVerificationEmail(User user) {
        String verificationLink = "This your verification mail: \n You should Click This Link to be verified \n https://tameit.azurewebsites.net/api/auth/verify-email?token=" + user.getVerificationToken();
        log.info("Link is sent" + verificationLink);
        emailSenderService.sendVerificationEmail(user.getEmail(), "Verification Email",verificationLink);
    }
}

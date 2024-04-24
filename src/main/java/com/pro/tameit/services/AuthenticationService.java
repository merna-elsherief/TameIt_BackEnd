package com.pro.tameit.services;

import com.pro.tameit.config.JwtService;
import com.pro.tameit.dto.request.AuthenticationRequest;
import com.pro.tameit.dto.request.RegisterRequest;
import com.pro.tameit.dto.response.AuthenticationResponse;
import com.pro.tameit.dto.response.DetailsResponse;
import com.pro.tameit.models.ERole;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.pro.tameit.util.VerificationTokenUtil.generateToken;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;
    public AuthenticationResponse register(RegisterRequest request){
        String token = generateToken();
        var user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verificationToken(token)
                .role(ERole.USER)
                .build();
        userRepository.save(user);
        sendVerificationEmail(user);
        var jwtToken = jwtService.generateToken(user);
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
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public DetailsResponse details( String userName){
        var user = userRepository.findByUserName(userName)
                .orElseThrow();
        return DetailsResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .build();
    }
        private void sendVerificationEmail(User user) {
        String verificationLink = "https://tameit.azurewebsites.net/api/auth/verify-email?token=" + user.getVerificationToken();
        System.out.println("Link is sent" + verificationLink);
        emailSenderService.sendVerificationEmail(user.getEmail(), "Verification Email",verificationLink);
    }
}

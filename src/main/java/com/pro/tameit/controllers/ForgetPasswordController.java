package com.pro.tameit.controllers;

import com.pro.tameit.models.ForgetPassword;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.ForgetPasswordRepository;
import com.pro.tameit.repo.UserRepository;
import com.pro.tameit.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.pro.tameit.dto.request.ResetPasswordRequest;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import static com.pro.tameit.util.OtpUtil.generateOtp;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ForgetPasswordController {

    private final UserRepository userRepo;
    private final EmailSenderService emailSenderService;
    private final ForgetPasswordRepository forgetPasswordRepo;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/auth/forgotPassword/verifyEmail/{userName}")
    public ResponseEntity<?> verifyEmail(@PathVariable String userName){
        User user = userRepo.findByUserName(userName).orElseThrow(()->new RuntimeException("Please provide an valid userName!" + userName));
        String otp = generateOtp();
        //Create New OTP And Insert It To The DB
        ForgetPassword fp = ForgetPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .user(user)
                .build();
        forgetPasswordRepo.save(fp);
        //Send The OTP To The User
        emailSenderService.sendVerificationEmail(user.getEmail(),
                "OTP For Forgot Password",
                "This is The OTP Requested" + otp);
        return ResponseEntity.ok("Email Sent For Verification");
    }
    @PostMapping("/auth/forgotPassword/verifyOtp/{otp}/{userName}")
    public ResponseEntity<?> verifyOtp(@PathVariable String otp, @PathVariable String userName){
        User user = userRepo.findByUserName(userName).orElseThrow(()->new RuntimeException("Please provide an valid userName!" + userName));
        // Check If the OTP is correct or not
        ForgetPassword fp = forgetPasswordRepo.findByOtpAndUser(otp, user)
                .orElseThrow(()-> new RuntimeException("Invalid OTP for userName: " + userName));
        // Check If the entered OTP is out of its validation time or not
        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgetPasswordRepo.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP Verified");
    }
    @PostMapping("/auth/forgotPassword/resetPassword/{userName}")
    public ResponseEntity<?> resetPasswordHandler(@RequestBody ResetPasswordRequest request
            , @PathVariable String userName){
        //Check If New Password == Confirm Password or not
        if (!Objects.equals(request.getNewPassword(), request.getConfirmNewPassword())){
            return new ResponseEntity<>("New password and confirm new password don't match", HttpStatus.EXPECTATION_FAILED);
        }
        //Encode The New Password
        String passwordEncoded = passwordEncoder.encode(request.getNewPassword());
        //Update The NewPassword By The repo Layer
        userRepo.updatePassword(userName, passwordEncoded);

        return ResponseEntity.ok("Password Has been changed");
    }
}

package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final MsgOtpService msgOtpService;
    private final PasswordEncoder passwordEncoder;

    //  Send OTP
    public String sendOtp(String mobile) {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found with this mobile "));

        return msgOtpService.sendOtp(user.getMobile());
    }

    //  Verify OTP + Reset Password
    public String resetPassword(String mobile, String otp, String newPassword) {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found "));

        //  Correct OTP verify call
        String verifyResponse = msgOtpService.verifyOtp(user.getId(), mobile, otp);

        // Correct success check
        boolean verified = verifyResponse != null &&
                verifyResponse.toLowerCase().contains("success");

        if (!verified) {
            return "OTP Invalid";
        }

        // Always encode password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password Reset Successfully ";
    }
}

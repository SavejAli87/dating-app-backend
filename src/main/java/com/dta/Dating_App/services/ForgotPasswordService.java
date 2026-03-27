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

    // ✅ SEND OTP
    public String sendOtp(String mobile) {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found with this mobile"));

        msgOtpService.sendOtp(user.getMobile());

        return "OTP Sent Successfully";
    }

    // ✅ RESET PASSWORD
    public String resetPassword(String mobile, String otp, String newPassword) {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ OTP verify (NEW WAY)
        boolean isValidOtp = msgOtpService.verifyOtp(mobile, otp);

        if (!isValidOtp) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // ✅ Password validation (optional but recommended)
        if (newPassword == null || newPassword.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        // ✅ Encode password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password Reset Successfully";
    }
}
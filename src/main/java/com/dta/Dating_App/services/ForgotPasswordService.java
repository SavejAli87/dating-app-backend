package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final MsgOtpService msgOtpService;

    //send otp
    public String sendOtp(String mobile){

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not Found with this mobile"));

        return msgOtpService.sendOtp(user.getMobile());
    }

    // Verify Otp + Reset password
    public String resetPassword(String mobile, String otp, String newPassword) {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        String verifyResponse = msgOtpService.verifyOtp(mobile,otp);

        if(!verifyResponse.contains("success")){
            return "OTP Invalid";
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        return "Password Reset Successfully";
    }
}

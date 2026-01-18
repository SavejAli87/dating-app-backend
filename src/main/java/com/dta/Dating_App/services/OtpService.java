package com.dta.Dating_App.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final JavaMailSender mailSender;

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    //  Send OTP
    public String sendOtp(String email) {

        String otp = generateOtp();
        otpStore.put(email, otp);

        //  Send mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + "\n\nDo not share this OTP with anyone.");

        mailSender.send(message);

        return "OTP sent successfully to email ";
    }

    //  Verify OTP
    public String verifyOtp(String email, String otp) {

        if (!otpStore.containsKey(email)) {
            return "OTP not found. Please request again.";
        }

        String savedOtp = otpStore.get(email);

        if (savedOtp.equals(otp)) {
            otpStore.remove(email);
            return "OTP verified successfully ";
        }

        return "Invalid OTP ";
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}

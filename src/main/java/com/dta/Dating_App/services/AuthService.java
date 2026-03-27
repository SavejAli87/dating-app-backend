package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.RegisterRequest;
import com.dta.Dating_App.JWTUtility.JwtService;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final MsgOtpService otpService;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, RegisterRequest> tempUsers = new HashMap<>();



    public Map<String, String> initRegister(RegisterRequest request) {

        String sessionId = UUID.randomUUID().toString();

        tempUsers.put(sessionId, request);

        otpService.sendOtp(request.getMobile());

        return Map.of(
                "message", "OTP Sent",
                "sessionId", sessionId
        );
    }


    public String verifyAndRegister(String sessionId, String otp) {

        RegisterRequest request = tempUsers.get(sessionId);

        if (request == null) {
            throw new RuntimeException("Session expired");
        }

        boolean isValidOtp = otpService.verifyOtp(
                request.getMobile(),
                otp
        );

        if (!isValidOtp) {
            throw new RuntimeException("Invalid OTP");
        }

        User user = User.builder()
                .name(request.getName())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);

        tempUsers.remove(sessionId);

        return jwtService.generateToken(user.getMobile());
    }

    // LOGIN
    public String login(String mobile, String password) {

        //  Basic validation
        if (mobile == null || password == null) {
            throw new RuntimeException("Mobile and password are required");
        }

        //  Find user
        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        //  Check deleted user
        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new RuntimeException("Account is deactivated");
        }

        //  Password check
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        //  Generate JWT
        return jwtService.generateToken(user.getMobile());
    }
}
package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.LoginResponse;
import com.dta.Dating_App.DTO.RegisterRequest;
import com.dta.Dating_App.JWTUtility.JwtService;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserProfileRepository;
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
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, RegisterRequest> tempUsers = new HashMap<>();

    // ================= INIT REGISTER =================
    public Map<String, String> initRegister(RegisterRequest request) {

        String sessionId = UUID.randomUUID().toString();

        tempUsers.put(sessionId, request);

        otpService.sendOtp(request.getMobile());

        return Map.of(
                "message", "OTP Sent",
                "sessionId", sessionId
        );
    }

    // ================= VERIFY + REGISTER =================
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

        //  Create User with userId
        User user = User.builder()
                .name(request.getName())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .userId(generateUserCode(request.getName()))
                .build();

        userRepository.save(user);

        //  Create Profile
        UserProfile profile = UserProfile.builder()
                .user(user)
                .name(request.getName())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .gender(request.getGender())
                .build();

        userProfileRepository.save(profile);

        tempUsers.remove(sessionId);

        return jwtService.generateToken(user.getMobile());
    }

    // ================= USER CODE GENERATOR =================
    private String generateUserCode(String name) {

        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Name is required");
        }

        String prefix = name.length() >= 2
                ? name.substring(0, 2).toUpperCase()
                : name.toUpperCase();

        User lastUser = userRepository.findTopByOrderByIdDesc();

        long number = 1000;

        if (lastUser != null && lastUser.getUserId() != null) {
            try {
                number = Long.parseLong(lastUser.getUserId().substring(2)) + 1;
            } catch (Exception e) {
                number = 1000;
            }
        }

        return prefix + number;
    }

    // ================= LOGIN =================
    public LoginResponse login(String mobile, String password) {

        if (mobile == null || password == null) {
            throw new RuntimeException("Mobile and password are required");
        }

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new RuntimeException("Account is deactivated");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getMobile());
        String sessionId = UUID.randomUUID().toString();

        return LoginResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .sessionId(sessionId)
                .username(user.getName())
                .build();
    }
}
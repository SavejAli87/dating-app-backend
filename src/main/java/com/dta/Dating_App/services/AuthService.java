package com.dta.Dating_App.services;

import com.dta.Dating_App.JWTUtility.JwtService;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void register(
            String name,
            String password,
            String gender,
            String bio,
            String displayName,
            LocalDate dob
    ) {

        if (userRepository.existsByDisplayName(displayName)) {
            throw new RuntimeException("Display name already taken");
        }

        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 18) {
            throw new RuntimeException("Age must be 18+");
        }

        User user = User.builder()
                .name(name)
                .password(password) // plain (testing)
                .gender(gender)
                .bio(bio)
                .dob(dob)
                .age(age)
                .role("USER")
                .displayName(displayName)
                //.active(true)
                .build();

        userRepository.save(user);
    }

    // Login using displayName + password
    public String login(String mobile, String password) {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(user.getDisplayName());
    }
}
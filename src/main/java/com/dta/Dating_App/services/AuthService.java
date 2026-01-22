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

    public void register(String name, String email, String password, String gender, String bio, String displayName, LocalDate dob) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        if(userRepository.existsByDisplayName(displayName)){
            throw new RuntimeException("Display name already taken");
        }

        int age = Period.between(dob,LocalDate.now()).getYears();

        if(age < 18){
            throw  new RuntimeException("Age must be 18+");
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .age(age)
                .password(password) // plain password (testing)
                .gender(gender)
                .bio(bio)
                .dob(dob)
                .role("USER")
                .displayName(displayName)
                .build();

        userRepository.save(user);
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }



        //  Return JWT Token
        return jwtService.generateToken(user.getEmail());
    }
}

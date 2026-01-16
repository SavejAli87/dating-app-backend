package com.dta.Dating_App.services;

import com.dta.Dating_App.JWTUtility.JwtService;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void register(String name, String email, String password, String gender, int age, String bio) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(password) // plain password (testing)
                .gender(gender)
                .age(age)
                .bio(bio)
                .role("USER")
                .build();

        userRepository.save(user);
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // ✅ Return JWT Token
        return jwtService.generateToken(user.getEmail());
    }
}

package com.dta.Dating_App.controller;

import com.dta.Dating_App.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> body) {

        String name = (String) body.get("name");
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        String gender = (String) body.get("gender");
        String bio = (String) body.get("bio");

        Object ageValue = body.get("age");
        int age = (ageValue == null) ? 0 : Integer.parseInt(ageValue.toString());

        authService.register(name, email, password, gender, age, bio);

        return ResponseEntity.ok("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        String token = authService.login(email, password);

        return ResponseEntity.ok(Map.of(
                "message", "Login Successful",
                "token", token
        ));
    }
}

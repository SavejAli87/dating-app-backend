package com.dta.Dating_App.controller;

import com.dta.Dating_App.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> body) {

        String name = (String) body.get("name");
       // String mobile = (String) body.get("mobile");
        String password = (String) body.get("password");
        String gender = (String) body.get("gender");
        String bio = (String) body.get("bio");
        LocalDate dob = LocalDate.parse(body.get("dob").toString());
        String displayName = (String) body.get("displayName");

        Object ageValue = body.get("age");
        int age = (ageValue == null) ? 0 : Integer.parseInt(ageValue.toString());

        authService.register(name,  password, gender, bio, displayName,dob);

        return ResponseEntity.ok("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {

        String mobile = body.get("mobile");
        String password = body.get("password");

        String token = authService.login(mobile, password);

        return ResponseEntity.ok(Map.of(
                "message", "Login Successful",
                "token", token
        ));
    }
}

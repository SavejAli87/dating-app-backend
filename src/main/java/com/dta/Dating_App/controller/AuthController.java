package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.LoginRequest;
import com.dta.Dating_App.DTO.LoginResponse;
import com.dta.Dating_App.DTO.RegisterRequest;
import com.dta.Dating_App.DTO.RegisterResponse;
import com.dta.Dating_App.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> initRegister(@RequestBody RegisterRequest request) {

        Map<String, String> response = authService.initRegister(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-register/otp")
    public ResponseEntity<?> verifyRegister(@RequestBody Map<String, String> body) {

        RegisterResponse response = authService.verifyAndRegister(
                body.get("sessionId"),
                body.get("otp")
        );

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User Registered",
                "token", response.getToken(),
                "userId", response.getUserId(),
                "ID",response.getId(),
                "gender", response.getGender(),
                "sessionId", response.getSessionId(),
                "username", response.getUsername()
        ));
    }

    // login

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (request.getMobile() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Mobile and password are required"
            ));
        }

        try {
            LoginResponse loginResponse = authService.login(request.getMobile(), request.getPassword());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Login Successful",
                    "token", loginResponse.getToken(),
                    "userId", loginResponse.getUserId(),
                    "ID", loginResponse.getId(),
                    "gender", loginResponse.getGender(),
                    "sessionId", loginResponse.getSessionId(),
                    "username", loginResponse.getUsername()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
}

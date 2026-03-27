package com.dta.Dating_App.controller;


import com.dta.Dating_App.DTO.ResetPasswordRequest;
import com.dta.Dating_App.DTO.SendOtpRequest;
import com.dta.Dating_App.services.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody SendOtpRequest request) {
        return ResponseEntity.ok(
                forgotPasswordService.sendOtp(request.getMobile())
        );
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {

        return ResponseEntity.ok(
                forgotPasswordService.resetPassword(
                        request.getMobile(),
                        request.getOtp(),
                        request.getNewPassword()
                )
        );
    }
}
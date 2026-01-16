package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.ForgotPasswordRequest;
import com.dta.Dating_App.DTO.ResetPasswordRequest;
import com.dta.Dating_App.services.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    // Send OTP
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request){
        return ResponseEntity.ok(forgotPasswordService.sendOtp(request.getMobile()));
    }

    //Reset Password
    @PostMapping("/reset-password")
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

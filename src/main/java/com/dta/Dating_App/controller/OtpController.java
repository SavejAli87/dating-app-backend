package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.SendOtpRequest;
import com.dta.Dating_App.DTO.VerifyOtpRequest;
import com.dta.Dating_App.services.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;

    //sent OTP
    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestBody SendOtpRequest request){
        return ResponseEntity.ok(otpService.sendOtp(request.getEmail()));
    }

    // Verify OTP
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok(otpService.verifyOtp(request.getEmail(), request.getOtp()));
    }

}

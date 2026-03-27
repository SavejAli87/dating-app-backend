package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.MsgSendOtp;
import com.dta.Dating_App.DTO.MsgVerifyOtp;
import com.dta.Dating_App.services.MsgOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MsgOtpController {

    private final MsgOtpService msgOtpService;

    //  SEND OTP
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody MsgSendOtp request) {

        return ResponseEntity.ok(
                msgOtpService.sendOtp(request.getMobile())
        );
    }

    //  VERIFY OTP (optional use)
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody MsgVerifyOtp request) {

        boolean isValid = msgOtpService.verifyOtp(
                request.getMobile(),
                request.getOtp()
        );

        if (!isValid) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        return ResponseEntity.ok("OTP Verified Successfully");
    }
}
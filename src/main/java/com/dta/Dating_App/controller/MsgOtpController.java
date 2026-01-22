package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.MsgSendOtp;
import com.dta.Dating_App.DTO.MsgVerifyOtp;
import com.dta.Dating_App.services.MsgOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
public class MsgOtpController {
    private final MsgOtpService msgOtpService;

    // Send otp
    @PostMapping("/msgSend")
    public ResponseEntity<String> sendOtp(@RequestBody MsgSendOtp request){
        return ResponseEntity.ok(msgOtpService.sendOtp(request.getMobile()));
    }

    //Verify OTP
    @PostMapping("/msgVerify")
    public ResponseEntity<String> verifyOtp(@RequestBody MsgVerifyOtp request){
        return ResponseEntity.ok(
                msgOtpService.verifyOtp(request.getUserId(), request.getMobile(), request.getOtp())
        );
    }

}

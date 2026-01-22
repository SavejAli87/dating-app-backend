package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class MsgVerifyOtp {
    private Long userId;
    private String mobile;
    private String otp;
}

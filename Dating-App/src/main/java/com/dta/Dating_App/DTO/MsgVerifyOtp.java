package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class MsgVerifyOtp {
    private String mobile;
    private String otp;
}

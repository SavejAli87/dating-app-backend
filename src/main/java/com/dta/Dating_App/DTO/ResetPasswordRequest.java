package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String mobile;
    private String otp;
    private String newPassword;

}

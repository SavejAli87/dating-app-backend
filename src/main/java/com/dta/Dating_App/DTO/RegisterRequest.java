package com.dta.Dating_App.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequest {

    private String name;
    private String mobile;
    private String password;
    private String confirmPassword;
    private String gender;
    //private String otp;
}
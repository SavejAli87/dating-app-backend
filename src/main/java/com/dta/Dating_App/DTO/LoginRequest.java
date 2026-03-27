package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class LoginRequest {

    private String mobile;
    private String password;
}
package com.dta.Dating_App.DTO;


import lombok.Data;

@Data
public class GenderOrientationRequest {
    private Long userId;
    private String gender;
    private String orientation;
}
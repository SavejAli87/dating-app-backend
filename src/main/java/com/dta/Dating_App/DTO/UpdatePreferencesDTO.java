package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class UpdatePreferencesDTO {
    private Long userId;
    private String lookingFor;
    private String smoke;
    private String drink;
}
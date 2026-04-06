package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class SupportRequestDTO {

    private Long userId;
    private String subject;
    private String message;
}
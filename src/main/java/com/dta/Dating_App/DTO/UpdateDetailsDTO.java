package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class UpdateDetailsDTO {
    private Long userId;
    private String language;
    private String bodyType;
    private String appearance;
    private Integer height;
}
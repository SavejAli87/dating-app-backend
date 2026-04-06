package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class UpdateBasicDTO {
    private Long userId;
    private String displayName;
    private String bio;
    private Integer age;
}
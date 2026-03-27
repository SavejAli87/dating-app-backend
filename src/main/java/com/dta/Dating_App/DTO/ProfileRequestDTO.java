package com.dta.Dating_App.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProfileRequestDTO {

    private String displayName;
    private String gender;
    private String orientation;
    private Integer age;
    private String bio;

    @Schema(example = "2002-05-15")
    private String dob;

    private String language;
    private String appearance;
    private String bodyType;
    private Integer height;
    private String englishLevel;
    private String ethnicity;
    private String lookingFor;
    private String smoke;
    private String drink;
}
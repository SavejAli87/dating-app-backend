package com.dta.Dating_App.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProfileResponse {

    private Long id;
    private String name;
    private String displayName;
    private String email;
//    private int age;
    private String bio;

    private String language;
    private String appearance;
    private String bodyType;
    private Integer height;
    private String englishLevel;
    private String ethnicity;
    private String smoke;
    private String drink;

    private boolean verifiedSelfie;

    private String profileImageUrl;
    private List<String> images; // gallery image urls

}

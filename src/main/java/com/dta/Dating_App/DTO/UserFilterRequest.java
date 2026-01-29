package com.dta.Dating_App.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserFilterRequest {

    private Long userId;

    // Search
    private String search;// name/displayName

    // Age Range
    private Integer minAge;
    private Integer maxAge;

    // Height Range
    private Integer minHeight;
    private Integer maxHeight;

    // Multi values
    private List<String> bodyType;
    private List<String> appearance;
    private List<String> language;
    private List<String> englishLevel;
    private List<String> ethnicity;
    private List<String> lookingFor;
    private List<String> gender;

    // smoke drink
    private Boolean smoke;
    private Boolean drink;

    // Distance
    private Integer maxDistanceKm;
    private Boolean worldwide;

    // Online filter
    private Boolean onlyOnline;

    // Pagination
    private Integer page = 0;
    private Integer size = 10;

}

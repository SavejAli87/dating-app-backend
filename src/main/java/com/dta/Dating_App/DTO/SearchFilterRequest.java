package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class SearchFilterRequest {

    private Integer minAge;
    private Integer maxAge;

    private String language;
    private String ethnicity;
    private String smoke;
    private String drink;

    private  String sortBy;

}

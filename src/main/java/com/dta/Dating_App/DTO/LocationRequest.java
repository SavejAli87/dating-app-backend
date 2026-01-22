package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class LocationRequest {

    private Long userId;
    private String city;
    private String state;
    private String country;
    private Double lat;
    private Double lng;
}

package com.dta.Dating_App.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NearbyUserResponse {

    private String name;
    private String city;
    private Double distance;
}
package com.dta.Dating_App.DTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchResponse {

    private String name;
    private Integer age;
    private String currentCity;
    private String bio;
    private String profileImageUrl;
}
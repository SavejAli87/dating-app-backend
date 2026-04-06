package com.dta.Dating_App.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCardDTO {

    private String name;
    private String profileImageUrl;
    private int age;
}
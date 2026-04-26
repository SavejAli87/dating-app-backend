package com.dta.Dating_App.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private String token;
    private String userId;
    private String sessionId;
    private String username;

    private Long id;
    private String gender;

}

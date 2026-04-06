package com.dta.Dating_App.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReportResponse {

    private Long id;
    private Long reportedUserId;
    private Long reportedById;
    private String reason;
    private String message;
    private String createdAt;
}
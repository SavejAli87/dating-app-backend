package com.dta.Dating_App.DTO;

import lombok.Data;

@Data
public class ReportRequestDTO {
    private Long byUserId;
    private Long targetUserId;
    private String reason;
    private String message;
}
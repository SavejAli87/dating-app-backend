package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.ReportRequestDTO;
import com.dta.Dating_App.DTO.ResolveReportDTO;
import com.dta.Dating_App.DTO.UserReportResponse;
import com.dta.Dating_App.entitys.UserReport;
import com.dta.Dating_App.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    // report a user
    @PostMapping("/report")
    public ResponseEntity<String> reportUser(@RequestBody ReportRequestDTO request){

        return ResponseEntity.ok(
                reportService.reportUser(
                        request.getByUserId(),
                        request.getTargetUserId(),
                        request.getReason()
                )
        );
    }

    //Get reports against a user ( Admin )
    @GetMapping("/against")
    public ResponseEntity<List<UserReportResponse>> reportsAgainstUser(
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(reportService.getReportsAgainstUser(userId));
    }

    // get report me
    @GetMapping("/my")
    public ResponseEntity<List<UserReportResponse>> myReports(
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(reportService.getMyReports(userId));
    }

    // resolve report(Admin)
    @PutMapping("/resolve/{reportId}")
    public ResponseEntity<String> resolve(
            @PathVariable Long reportId,
            @RequestBody ResolveReportDTO dto
    ){
        return ResponseEntity.ok(
                reportService.resolveReport(reportId, dto.getStatus())
        );
    }
}

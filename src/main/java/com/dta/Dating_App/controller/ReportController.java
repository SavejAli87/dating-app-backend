package com.dta.Dating_App.controller;

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
    public ResponseEntity<String> reportUser(@RequestBody Map<String, String> body){

        Long byUserId = Long.valueOf(body.get("byUserId"));
        Long targetUserId = Long.valueOf(body.get("targetUserId"));
        String reason = body.get("reason");

        return ResponseEntity.ok(reportService.reportUser(byUserId, targetUserId, reason));
    }

    //Get reports against a user ( Admin )
    @GetMapping("/against")
    public ResponseEntity<List<UserReport>> reportsAgainstUser(
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(reportService.getReportsAgainstUser(userId));
    }
    // get my reports
    @GetMapping("/my")
    public ResponseEntity<List<UserReport>> myReports(
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(reportService.getMyReports(userId));
    }

    // resolve report(Admin)
    @PutMapping("/resolve/{reportId}")
    public ResponseEntity<String> resolve(
            @PathVariable Long reportId,
            @RequestParam String status
    ){
        return ResponseEntity.ok(reportService.resolveReport(reportId, status));
    }
}

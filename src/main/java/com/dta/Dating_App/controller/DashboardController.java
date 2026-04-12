package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    // 🟢 Online tab
    @GetMapping("/online")
    public ResponseEntity<Page<UserProfile>> online(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                dashboardService.getOnlineUsers(page, size)
        );
    }

    //  Recent joined tab
    @GetMapping("/recent")
    public ResponseEntity<Page<UserProfile>> recent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                dashboardService.getRecentJoinedUsers(page, size)
        );
    }
}
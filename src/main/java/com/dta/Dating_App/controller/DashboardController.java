package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // Online tab
    @GetMapping("/online")
    public ResponseEntity<Page<User>> online(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(dashboardService.getOnlineUsers(page, size));
    }

    // recent joined tab
    @GetMapping("/recent")
    public ResponseEntity<Page<User>> recent(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(dashboardService.getRecentJoinedUsers(page, size));
    }
}

package com.dta.Dating_App.controller;

import com.dta.Dating_App.services.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChangePasswordController {

    private final ChangePasswordService service;

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ){
        return ResponseEntity.ok(service.changePassword(userId,oldPassword, newPassword));
    }

}

package com.dta.Dating_App.controller;

import com.dta.Dating_App.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/deactivate")
    public ResponseEntity<String> softDelete(@RequestParam Long userId){
        return ResponseEntity.ok(accountService.softDelete(userId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> hardDelete(@RequestParam Long userId){
        return ResponseEntity.ok(accountService.hardDelete(userId));
    }
}

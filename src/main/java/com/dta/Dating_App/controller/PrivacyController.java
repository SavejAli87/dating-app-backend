package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.TermsAcceptance;
import com.dta.Dating_App.services.PrivacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/privacy")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PrivacyController {

    private final PrivacyService privacyService;

    // accept terms
    @PostMapping("/accept")
    public ResponseEntity<String> acceptTerms(
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(privacyService.acceptTerms(userId));
    }

    // check if terms accepted
    @GetMapping("/status")
    public ResponseEntity<Boolean> status(
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(privacyService.hasAcceptedTerms(userId));
    }

    // get acceptance details (optional)
    @GetMapping("/details")
    public ResponseEntity<TermsAcceptance> details(
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(privacyService.getAcceptance(userId));
    }

 }

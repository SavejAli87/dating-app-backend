package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.Subscriber;
import com.dta.Dating_App.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriber")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    //  Activate Plan
    @PostMapping("/activate")
    public ResponseEntity<String> activate(
            @RequestParam String userId,
            @RequestParam String type
    ) {
        return ResponseEntity.ok(
                subscriptionService.activatePlan(userId, type)
        );
    }

    //  Get User Plan
    @GetMapping("/status")
    public ResponseEntity<Subscriber> getStatus(
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(
                subscriptionService.getUserPlan(userId)
        );
    }

    //  Remaining Days
    @GetMapping("/remaining-days")
    public ResponseEntity<Integer> remainingDays(
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(
                subscriptionService.getRemainingDays(userId)
        );
    }
}
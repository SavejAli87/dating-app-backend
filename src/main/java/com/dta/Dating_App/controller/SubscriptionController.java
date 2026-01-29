package com.dta.Dating_App.controller;

import com.dta.Dating_App.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    // Send Subscription request

    @PostMapping("/request")
    public ResponseEntity<String> sendRequest(
            @RequestParam Long senderId,
            @RequestParam Long receiverId
    ){
        return ResponseEntity.ok(subscriptionService.sendRequest(senderId, receiverId));
    }

    // Respond to request (Approved / Rejected)
    @PutMapping("/respond")
    public ResponseEntity<String> respond(
            @RequestParam Long requestId,
            @RequestParam String status
    ){
        return ResponseEntity.ok(subscriptionService.respond(requestId, status));
    }

    //Activate Subscription (Free / gold/ Premium)

    @PostMapping("/activate")
    public ResponseEntity<String> activate(
            @RequestParam Long userId,
            @RequestParam String plan
    ){
        return ResponseEntity.ok(subscriptionService.activate(userId, plan));
    }

    //Get current subscription status
    @GetMapping("/status")
    public ResponseEntity<?> status(@RequestParam Long userId){
        return ResponseEntity.ok(subscriptionService.getStatus(userId));
    }
}

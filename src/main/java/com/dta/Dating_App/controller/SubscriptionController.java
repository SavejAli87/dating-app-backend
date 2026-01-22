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

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam Long senderId, @RequestParam Long receiverId){
        return ResponseEntity.ok(subscriptionService.sendRequest(senderId,receiverId));

    }

    @PutMapping("/respond")
    public ResponseEntity<String> response(@RequestParam Long requestId, @RequestParam String status){
        return ResponseEntity.ok(subscriptionService.respond(requestId,status));
    }
}

package com.dta.Dating_App.controller;

import com.dta.Dating_App.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    //  PUSH notification (Admin / System / Testing)
    @PostMapping("/push")
    public ResponseEntity<String> push(@RequestBody Map<String, String> body) {

        Long userId = Long.valueOf(body.get("userId"));
        String message = body.get("message");

        notificationService.push(userId, message);
        return ResponseEntity.ok("Notification pushed ");
    }

    // Get all notifications of a user
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Long userId){
        return ResponseEntity.ok(notificationService.getAll(userId));
    }


    @PutMapping("/read/{notificationId}")
    public ResponseEntity<String> markRead(@RequestParam Long notificationId){
        notificationService.markRead(notificationId);
        return ResponseEntity.ok("Marked as read");
    }

}

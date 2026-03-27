package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OnlineStatusController {

    private final UserProfileRepository userProfileRepository;

    @PutMapping("/online")
    public ResponseEntity<String> setOnline(@RequestParam Long userId){

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setOnline(true);
        profile.setLastSeen(LocalDateTime.now());

        userProfileRepository.save(profile);

        return ResponseEntity.ok("User is online");
    }

    @PutMapping("/offline")
    public ResponseEntity<String> setOffline(@RequestParam Long userId){

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setOnline(false);
        profile.setLastSeen(LocalDateTime.now());

        userProfileRepository.save(profile);

        return ResponseEntity.ok("User is Offline");
    }
}
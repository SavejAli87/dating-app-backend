package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class OnlineStatusController {

    private  final UserRepository userRepository;

    @PutMapping("/online")
    public ResponseEntity<String> setOnline(@RequestParam Long userId){

        User user   = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        user.setOnline(true);
        user.setLastSeen(LocalDateTime.now());

        userRepository.save(user);

        return ResponseEntity.ok("User is online");
    }

    @PutMapping("/offline")
    public ResponseEntity<String> setOffline(@RequestParam Long userId){

        User user  = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setOnline(false);
        user.setLastSeen(LocalDateTime.now());

        userRepository.save(user);

        return ResponseEntity.ok("User is Offline");
    }
}

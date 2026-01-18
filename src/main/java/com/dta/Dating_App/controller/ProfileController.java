package com.dta.Dating_App.controller;

import com.dta.Dating_App.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{userId}/setup")
    public ResponseEntity<String> setupProfile(
            @PathVariable Long userId,
            @RequestParam String displayName,
            @RequestParam(required = false)MultipartFile photo
            ){
        profileService.setupProfile(userId,displayName,photo);
        return ResponseEntity.ok("Profile setup done ");
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(
            @RequestParam Long userId,
            @RequestParam MultipartFile image
    ){
        profileService.uploadImage(userId,image);
        return ResponseEntity.ok("Image Uploaded");
    }

    @PostMapping("/gender-orientation")
    public ResponseEntity<String> saveGenderOrientation(@RequestBody Map<String, String> body){

        Long userId = Long.valueOf(body.get("UserId"));
        String gender = body.get("gender");
        String orientation = body.get("orientation");

        profileService.saveGenderOrientation(userId, gender, orientation);

        return ResponseEntity.ok("Gender & Orientation Saved ");

    }
}

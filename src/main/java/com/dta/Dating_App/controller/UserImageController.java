package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.UserImage;
import com.dta.Dating_App.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserImageController {

    private final ProfileService profileService;

    // ================== UPLOAD IMAGE ==================
    @PostMapping(value = "/{userId}/images", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> uploadImage(
            @PathVariable String userId,   //  IMPORTANT
            @RequestParam MultipartFile image
    ){
        profileService.uploadImageByUserCode(userId, image);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Image uploaded successfully");
        response.put("status", true);

        return ResponseEntity.ok(response);
    }

    // ================== GET ALL IMAGES ==================
    @GetMapping("/{userId}/images")
    public ResponseEntity<Map<String, Object>> getAllImages(@PathVariable String userId) {

        List<UserImage> images = profileService.getAllImageByUserCode(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("count", images.size());
        response.put("data", images);

        return ResponseEntity.ok(response);
    }

    // ================== DELETE IMAGE ==================
    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Map<String, Object>> deleteImage(@PathVariable Long imageId){

        String result = profileService.deleteImage(imageId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", result);
        response.put("status", true);

        return ResponseEntity.ok(response);
    }

    // ================== SET PROFILE PHOTO ==================
    @PutMapping("/{userId}/profile-photo/{imageId}")
    public ResponseEntity<Map<String, Object>> setProfilePhoto(
            @PathVariable Long userId,
            @PathVariable Long imageId
    ) {

        String result = profileService.setProfilePhoto(userId, imageId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", result);
        response.put("status", true);

        return ResponseEntity.ok(response);
    }


}


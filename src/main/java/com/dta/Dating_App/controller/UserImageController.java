package com.dta.Dating_App.controller;

import com.dta.Dating_App.entitys.UserImage;
import com.dta.Dating_App.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserImageController {

    private final ProfileService profileService;

//    // Upload Image
//    @PostMapping("/{userId}/images")
//    public ResponseEntity<String> uploadImage(
//            @PathVariable Long userId,
//            @RequestParam MultipartFile image
//            ){
//        return ResponseEntity.ok(profileService.uploadImage(userId, image));
//    }

    //Get all image
    @GetMapping("/{userId}/images")
    public ResponseEntity<List<UserImage>> getAllImages(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getAllImage(userId));
    }

    //Delete Photo
    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId){
        return ResponseEntity.ok(profileService.deleteImage(imageId));
    }

    // Set Profile Photo
    @PutMapping("/{userId}/profile-photo/{imageId}")
    public ResponseEntity<String> setProfilePhoto(
            @PathVariable Long userId,
            @PathVariable Long imageId
    ) {
        return ResponseEntity.ok(profileService.setProfilePhoto(userId, imageId));
    }
}

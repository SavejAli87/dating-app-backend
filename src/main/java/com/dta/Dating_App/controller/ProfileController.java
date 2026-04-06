package com.dta.Dating_App.controller;

import com.dta.Dating_App.DTO.*;
import com.dta.Dating_App.services.ProfileService;
import com.dta.Dating_App.services.ViewProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProfileController {

    private final ProfileService profileService;
    private final ViewProfileService viewProfileService;

    // ================== FULL PROFILE SETUP ==================
    @PostMapping(value = "/{userId}/setup", consumes = "multipart/form-data")
    public ResponseEntity<String> setupProfile(

            @PathVariable Long userId,

            @ModelAttribute ProfileRequestDTO dto,

            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {

        LocalDate parsedDob = null;
        if (dto.getDob() != null && !dto.getDob().isEmpty()) {
            parsedDob = LocalDate.parse(dto.getDob());
        }

        profileService.setupProfile(
                userId,
                dto.getDisplayName(),
                dto.getGender(),
                dto.getOrientation(),
                dto.getAge(),
                dto.getBio(),
                parsedDob,
                dto.getLanguage(),
                dto.getAppearance(),
                dto.getBodyType(),
                dto.getHeight(),
                dto.getEnglishLevel(),
                dto.getEthnicity(),
                dto.getLookingFor(),
                dto.getSmoke(),
                dto.getDrink(),
                photo
        );

        return ResponseEntity.ok("Profile setup done");
    }

    // ================== UPLOAD IMAGE ==================
    @PostMapping(value = "/upload-image", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadImage(
            @RequestParam Long userId,
            @RequestParam MultipartFile image
    ){
        profileService.uploadImage(userId, image);
        return ResponseEntity.ok("Image Uploaded");
    }

    // ================== GENDER + ORIENTATION ==================
    @PostMapping("/gender-orientation")
    public ResponseEntity<String> saveGenderOrientation(
            @RequestBody GenderOrientationRequest request){

        profileService.saveGenderOrientation(
                request.getUserId(),
                request.getGender(),
                request.getOrientation()
        );

        return ResponseEntity.ok("Gender & Orientation Saved");
    }

    // ================== VIEW MY PROFILE ==================
    @GetMapping("/me/{userId}")
    public ResponseEntity<ProfileResponse> myProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(viewProfileService.getMyProfile(userId));
    }

    // ================== SELFIE UPLOAD ==================
    @PostMapping(value = "/selfie/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadSelfie(
            @RequestParam Long userId,
            @RequestParam MultipartFile selfie
    ){
        // Future: AI verification
        return ResponseEntity.ok("Selfie uploaded (Verification pending)");
    }

    // ================== SELFIE VERIFY ==================
    @PutMapping("/selfie/verify/{userId}")
    public ResponseEntity<String> verifySelfie(@PathVariable Long userId) {

        String response = profileService.verifySelfie(userId);

        return ResponseEntity.ok(response);
    }

    // profile update basic

    @PutMapping("/update-basic")
    public ResponseEntity<String> updateBasic(@RequestBody UpdateBasicDTO dto){

        profileService.updateBasic(
                dto.getUserId(),
                dto.getDisplayName(),
                dto.getBio(),
                dto.getAge()
        );

        return ResponseEntity.ok("Basic profile updated");
    }

    // update details


    @PutMapping("/update-details")
    public ResponseEntity<String> updateDetails(@RequestBody UpdateDetailsDTO dto){

        profileService.updateDetails(
                dto.getUserId(),
                dto.getLanguage(),
                dto.getBodyType(),
                dto.getAppearance(),
                dto.getHeight()
        );

        return ResponseEntity.ok("Details updated");
    }

//   update preferences
@PutMapping("/update-preferences")
public ResponseEntity<String> updatePreferences(@RequestBody UpdatePreferencesDTO dto){

    profileService.updatePreferences(
            dto.getUserId(),
            dto.getLookingFor(),
            dto.getSmoke(),
            dto.getDrink()
    );

    return ResponseEntity.ok("Preferences updated");
}

    // get full profile

    @GetMapping("/completion/{userId}")
    public ResponseEntity<Integer> getProfileCompletion(@PathVariable Long userId) {

        int completion = profileService.getProfileCompletion(userId);

        return ResponseEntity.ok(completion);
    }

}
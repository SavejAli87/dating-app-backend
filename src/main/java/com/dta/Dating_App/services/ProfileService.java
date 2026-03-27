package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserImage;
import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserImageRepository;
import com.dta.Dating_App.repository.UserProfileRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final UserProfileRepository userProfileRepository;

    private final String uploadDir = "uploads/";

    // ================== COMMON IMAGE SAVE ==================
    private String saveImage(MultipartFile file) {
        try {

            if (file == null || file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Only image files allowed");
            }

            if (file.getSize() > 2 * 1024 * 1024) {
                throw new RuntimeException("File too large (Max 2MB)");
            }

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            Files.write(path, file.getBytes());

            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed");
        }
    }

    // ================== FULL PROFILE SETUP ==================
    public void setupProfile(
            Long userId,
            String displayName,
            String gender,
            String orientation,
            Integer age,
            String bio,
            LocalDate dob,
            String language,
            String appearance,
            String bodyType,
            Integer height,
            String englishLevel,
            String ethnicity,
            String lookingFor,
            String smoke,
            String drink,
            MultipartFile photo
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElse(UserProfile.builder().user(user).build());

        // ===== BASIC =====
        profile.setDisplayName(displayName);
        profile.setGender(gender);
        profile.setOrientation(orientation);
        profile.setAge(age);
        profile.setBio(bio);
        profile.setDob(dob);

        // ===== EXTRA =====
        profile.setLanguage(language);
        profile.setAppearance(appearance);
        profile.setBodyType(bodyType);
        profile.setHeight(height);
        profile.setEnglishLevel(englishLevel);
        profile.setEthnicity(ethnicity);
        profile.setLookingFor(lookingFor);
        profile.setSmoke(smoke);
        profile.setDrink(drink);

        // ===== IMAGE =====
        String fileName = saveImage(photo);
        if (fileName != null) {
            profile.setProfileImageUrl("/uploads/" + fileName);
        }

        userProfileRepository.save(profile);
    }

    // ================== UPLOAD MULTIPLE IMAGES ==================
    public void uploadImage(Long userId, MultipartFile image){

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        String fileName = saveImage(image);

        if (fileName == null) {
            throw new RuntimeException("Image upload failed");
        }

        UserImage userImage = UserImage.builder()
                .imageUrl("/uploads/" + fileName)
                .fileName(fileName)
                .user(user)
                .build();

        userImageRepository.save(userImage);
    }

    // ================== GET ALL IMAGES ==================
    public List<UserImage> getAllImage(Long userId){
        return userImageRepository.findByUserId(userId);
    }

    // ================== DELETE IMAGE ==================
    public String deleteImage(Long imageId){

        UserImage userImage = userImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        try{
            Path path = Paths.get(uploadDir + userImage.getFileName());

            Files.deleteIfExists(path);
            userImageRepository.delete(userImage);

            return "Image deleted successfully";

        } catch (Exception e) {
            throw new RuntimeException("Image delete failed");
        }
    }

    // ================== SET PROFILE PHOTO ==================
    public String setProfilePhoto(Long userId, Long imageId){

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // reset all
        List<UserImage> images = userImageRepository.findByUserId(userId);
        for(UserImage img : images) {
            img.setProfile(false);
        }
        userImageRepository.saveAll(images);

        // select new
        UserImage selectedImage = userImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if(!selectedImage.getUser().getId().equals(userId)){
            throw new RuntimeException("Invalid image for this user");
        }

        selectedImage.setProfile(true);
        userImageRepository.save(selectedImage);

        profile.setProfileImageUrl(selectedImage.getImageUrl());
        userProfileRepository.save(profile);

        return "Profile photo updated";
    }

    public void saveGenderOrientation(Long userId, String gender, String orientation){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElse(UserProfile.builder().user(user).build());

        profile.setGender(gender);
        profile.setOrientation(orientation);

        userProfileRepository.save(profile);
    }
}
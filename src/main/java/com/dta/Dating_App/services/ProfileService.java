package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserImage;
import com.dta.Dating_App.repository.UserImageRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    private final UserImageRepository userImageRepository;

    private final String uploadDir = "/uploads/";

    public void setupProfile(Long userId, String displayName, MultipartFile photo) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user Not Found"));

        // displayName unique check
        if(userRepository.existsByDisplayName(displayName)) {
            throw new RuntimeException("Display name already taken");
        }
        user.setDisplayName(displayName);

        if(photo != null && !photo.isEmpty()){

            try{
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);

                Files.write(filePath, photo.getBytes());

                String imageUrl = "http://localhost:9092/" + uploadDir + fileName;
                user.setProfileImageUrl(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException("Image upload failed");
            }
        }

        userRepository.save(user);
    }
  public void saveGenderOrientation(Long userId, String gender, String orientation){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));

        user.setGender(gender);
        user.setOrientation(orientation);

        userRepository.save(user);
  }

  public void uploadImage(Long userId, MultipartFile image){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        try{
            File dir = new File("/upload/");
            if(!dir.exists()) dir.mkdirs();

            String fileName = UUID.randomUUID() + "_" +image.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);

            Files.write(path, image.getBytes());

            String imageUrl = "http://localhost:9092/uploads/" + fileName;

            UserImage userImage = UserImage.builder()
                    .imageUrl(imageUrl)
                    .user(user)
                    .build();

            userImageRepository.save(userImage);
        } catch (Exception e)  {
            throw new RuntimeException("Image upload failed");
        }
  }

  // Get All images of user
    public List<UserImage> getAllImage(Long userId){
        return userImageRepository.findByUserId(userId);
    }

    // Delete Photo ( DB + file)
    public String deleteImage(Long imageId){

        UserImage userImage = userImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found "));

        try{
            // delete file from folder
            Path path = Paths.get(uploadDir + userImage.getFileName());
            Files.deleteIfExists(path);

            // delete from DB
            userImageRepository.delete(userImage);

            return "Image deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException("Image delete failed");
        }
    }

    // Set Profile Photo
    public String setProfilePhoto(Long userId, Long imageId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        // get all images and set false
        List<UserImage> images = userImageRepository.findByUserId(userId);
        for(UserImage img : images) {
            img.setProfile(false);
        }

        userImageRepository.saveAll(images);

        // Set selected images as profile
        UserImage selectedImage = userImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found "));

        if(!selectedImage.getUser().getId().equals(userId)){
            throw new RuntimeException("This image does not belong to this user");
        }

        selectedImage.setProfile(true);
        userImageRepository.save(selectedImage);

        // update user main profile image url
        user.setProfileImageUrl(selectedImage.getImageUrl());
        userRepository.save(user);

        return "Profile photo updated";
    }

}

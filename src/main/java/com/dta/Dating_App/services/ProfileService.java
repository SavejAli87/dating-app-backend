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

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final UserProfileRepository userProfileRepository;

    private final String uploadDir = "amara/";

    // ================== IMAGE SAVE ==================
    private String saveImage(MultipartFile file, String userId) {
        try {

            if (file == null || file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            if (!file.getContentType().startsWith("image/")) {
                throw new RuntimeException("Only image files allowed");
            }

            if (file.getSize() > 10 * 1024 * 1024) {
                throw new RuntimeException("Max 2MB allowed");
            }

            //  CHANGE: user-wise folder
            String folder = uploadDir + userId + "/";
            File dir = new File(folder);
            if (!dir.exists()) dir.mkdirs();

            String ext = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf("."));

            String fileName = "img_" + System.currentTimeMillis() + ext;

            Path path = Paths.get(folder + fileName);
            Files.write(path, file.getBytes());

            //  CHANGE: return user folder path
            return userId + "/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    // ================== PROFILE SETUP ==================
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

        if (displayName != null) profile.setDisplayName(displayName);
        if (gender != null) profile.setGender(gender);
        if (orientation != null) profile.setOrientation(orientation);
        if (age != null) profile.setAge(age);
        if (bio != null) profile.setBio(bio);
        if (dob != null) profile.setDob(dob);

        if (language != null) profile.setLanguage(language);
        if (appearance != null) profile.setAppearance(appearance);
        if (bodyType != null) profile.setBodyType(bodyType);
        if (height != null) profile.setHeight(height);
        if (englishLevel != null) profile.setEnglishLevel(englishLevel);
        if (ethnicity != null) profile.setEthnicity(ethnicity);
        if (lookingFor != null) profile.setLookingFor(lookingFor);
        if (smoke != null) profile.setSmoke(smoke);
        if (drink != null) profile.setDrink(drink);

        //  FIXED
        if (photo != null && !photo.isEmpty()) {
            String fileName = saveImage(photo, user.getUserId());
            profile.setProfileImageUrl("/amara/" + fileName);
        }

        userProfileRepository.save(profile);
    }

    // ================== UPLOAD IMAGE ==================
    public void uploadImage(Long userId, MultipartFile image){

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image required");
        }

        List<UserImage> list = userImageRepository.findByUserId(userId);
        if(list.size() >= 5){
            throw new RuntimeException("Max 5 images allowed");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileName = saveImage(image, user.getUserId());

        UserImage img = UserImage.builder()
                .imageUrl("/amara/" + fileName)
                .fileName(fileName)
                .user(user)
                .isProfile(false) //  FIX
                .build();

        userImageRepository.save(img);
    }

    // ================== GET IMAGES ==================
    public List<UserImage> getAllImage(Long userId){
        return userImageRepository.findByUserId(userId);
    }

    // ================== DELETE ==================
    public String deleteImage(Long imageId){

        UserImage img = userImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        try{
            //  CHANGE: correct full path (user folder)
            Path path = Paths.get(uploadDir + img.getFileName());
            Files.deleteIfExists(path);

            userImageRepository.delete(img);
            return "Deleted";

        } catch (Exception e) {
            throw new RuntimeException("Delete failed");
        }
    }

    // ================== SET PROFILE PHOTO ==================
    public String setProfilePhoto(Long userId, Long imageId){

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<UserImage> list = userImageRepository.findByUserId(userId);
        list.forEach(i -> i.setProfile(false));
        userImageRepository.saveAll(list);

        UserImage img = userImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if(!img.getUser().getId().equals(userId)){
            throw new RuntimeException("Invalid image");
        }

        img.setProfile(true);
        userImageRepository.save(img);

        profile.setProfileImageUrl(img.getImageUrl());
        userProfileRepository.save(profile);

        return "Profile updated";
    }

    // ================== GENDER ==================
    public void saveGenderOrientation(Long userId, String gender, String orientation){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElse(UserProfile.builder().user(user).build());

        if(gender != null) profile.setGender(gender);
        if(orientation != null) profile.setOrientation(orientation);

        userProfileRepository.save(profile);
    }

    // ================== VERIFY SELFIE ==================
    public String verifySelfie(Long userId){

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setSelfieVerified(true);
        userProfileRepository.save(profile);

        return "Verified";
    }

    // ================== UPDATE BASIC ==================
    public void updateBasic(Long userId, String name,String displayName, String bio, Integer age){

        UserProfile p = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if(name != null) p.setDisplayName(name);
        if(displayName != null) p.setDisplayName(displayName);
        if(bio != null) p.setBio(bio);
        if(age != null) p.setAge(age);

        userProfileRepository.save(p);
    }

    // ================== UPDATE DETAILS ==================
    public void updateDetails(Long userId, String language, String bodyType, String appearance, Integer height){

        UserProfile p = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if(language != null) p.setLanguage(language);
        if(bodyType != null) p.setBodyType(bodyType);
        if(appearance != null) p.setAppearance(appearance);
        if(height != null) p.setHeight(height);

        userProfileRepository.save(p);
    }

    // ================== UPDATE PREF ==================
    public void updatePreferences(Long userId, String lookingFor, String smoke, String drink){

        UserProfile p = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if(lookingFor != null) p.setLookingFor(lookingFor);
        if(smoke != null) p.setSmoke(smoke);
        if(drink != null) p.setDrink(drink);

        userProfileRepository.save(p);
    }

    // ================== COMPLETION ==================
    public int getProfileCompletion(Long userId){

        UserProfile p = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        int total = 8;
        int filled = 0;

        if(p.getDisplayName()!=null) filled++;
        if(p.getBio()!=null) filled++;
        if(p.getGender()!=null) filled++;
        if(p.getDob()!=null) filled++;
        if(p.getLanguage()!=null) filled++;
        if(p.getAppearance()!=null) filled++;
        if(p.getLookingFor()!=null) filled++;
        if(p.getProfileImageUrl()!=null) filled++;

        return (filled * 100) / total;
    }

    // updated image code


    public void uploadImageByUserCode(String userCode, MultipartFile image){

        User user = userRepository.findByUserId(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));

        uploadImage(user.getId(), image);
    }

    public List<UserImage> getAllImageByUserCode(String userCode){

        User user = userRepository.findByUserId(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return getAllImage(user.getId());
    }


}


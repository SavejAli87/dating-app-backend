package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.ProfileResponse;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserImage;
import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserImageRepository;
import com.dta.Dating_App.repository.UserProfileRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewProfileService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final UserProfileRepository userProfileRepository;

    public ProfileResponse getMyProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<UserImage> images = userImageRepository.findByUserId(userId);

        List<String> imageUrls = images.stream()
                .map(UserImage::getImageUrl)
                .toList();

        return ProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())

                //  PROFILE DATA
                .displayName(profile.getDisplayName())
                .bio(profile.getBio())
                .language(profile.getLanguage())
                .appearance(profile.getAppearance())
                .bodyType(profile.getBodyType())
                .height(profile.getHeight())
                .englishLevel(profile.getEnglishLevel())
                .ethnicity(profile.getEthnicity())
                .smoke(profile.getSmoke())
                .drink(profile.getDrink())
                .verifiedSelfie(profile.isVerifiedSelfie())
                .profileImageUrl(profile.getProfileImageUrl())

                // images
                .images(imageUrls)

                .build();
    }
}
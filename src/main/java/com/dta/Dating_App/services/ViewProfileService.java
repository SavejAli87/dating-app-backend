package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.ProfileResponse;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserImage;
import com.dta.Dating_App.repository.UserImageRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewProfileService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;

    public ProfileResponse getMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<UserImage> images = userImageRepository.findByUserId(userId);

        List<String> imageUrls = images.stream()
                .map(UserImage::getImageUrl)
                .toList();

        return ProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
//                .age(user.getAge())
                .bio(user.getBio())
                .language(user.getLanguage())
                .appearance(user.getAppearance())
                .bodyType(user.getBodyType())
                .height(user.getHeight())
                .englishLevel(user.getEnglishLevel())
                .ethnicity(user.getEthnicity())
                .smoke(user.getSmoke())
                .drink(user.getDrink())
                .verifiedSelfie(user.isVerifiedSelfie())
                .profileImageUrl(user.getProfileImageUrl())
                .images(imageUrls)
                .build();
    }

}

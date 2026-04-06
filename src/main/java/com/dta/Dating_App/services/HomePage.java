package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.UserCardDTO;
import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePage {

    private final UserProfileRepository userProfileRepository;

    public List<UserCardDTO> getOppositeUsers(String userId) {

        UserProfile currentUser = userProfileRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String gender = currentUser.getGender();

        if (gender == null) {
            throw new RuntimeException("Gender not set for this user");
        }

        String oppositeGender;

        if ("male".equalsIgnoreCase(gender)) {
            oppositeGender = "female";
        } else if ("female".equalsIgnoreCase(gender)) {
            oppositeGender = "male";
        } else {
            throw new RuntimeException("Invalid gender");
        }

        List<UserProfile> profiles =
                userProfileRepository.findByGenderAndUser_UserIdNot(oppositeGender, userId);

        // 🔥 Convert to DTO
        return profiles.stream().map(profile -> {

            int age = 0;
            if (profile.getDob() != null) {
                age = java.time.Period.between(profile.getDob(), java.time.LocalDate.now()).getYears();
            }

            return UserCardDTO.builder()
                    .name(profile.getUser().getName())
                    .profileImageUrl(profile.getProfileImageUrl())   // ya profile.getImages().get(0)
                    .age(age)
                    .build();

        }).toList();
    }
}
package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.LocationRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserLocation;
import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserLocationRepository;
import com.dta.Dating_App.repository.UserProfileRepository;
import com.dta.Dating_App.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final UserRepository userRepository;
    private final UserLocationRepository locationRepository;
    private final UserProfileRepository userProfileRepository;

    // ✅ Get current location
    public UserLocation getCurrentLocation(Long userId){
        return locationRepository.findByUserIdAndCurrentTrue(userId)
                .orElseThrow(() -> new RuntimeException("Current location not set"));
    }

    // ✅ Get location history (FIXED: String → Long)
    public List<UserLocation> getLocationHistory(Long userId) {
        return locationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // ✅ Add new location
    @Transactional
    public String addNewLocation(LocationRequest request){

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // 🔹 Step 1: old current location false
        locationRepository.findByUserIdAndCurrentTrue(request.getUserId())
                .ifPresent(loc -> loc.setCurrent(false));

        // 🔹 Step 2: create new location
        UserLocation location = UserLocation.builder()
                .user(user)   // (keep as is if you are using Long)
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .lat(request.getLat())
                .lng(request.getLng())
                .current(true)
                .createdAt(LocalDateTime.now())
                .build();

        locationRepository.save(location);

        // 🔹 Step 3: update profile
        profile.setCurrentCity(request.getCity());
        profile.setCurrentState(request.getState());
        profile.setCurrentCountry(request.getCountry());
        profile.setCurrentLat(request.getLat());
        profile.setCurrentLng(request.getLng());

        userProfileRepository.save(profile);

        return "New location added & set as current";
    }

    // ✅ Switch location
    @Transactional
    public String switchLocation(Long userId, Long locationId){

        // 🔹 Validate user
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        UserLocation target = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        // 🔹 Safety check
        if (target.getUser() == null || !target.getUser().getId().equals(userId)) {
            throw new RuntimeException("This location does not belong to user");
        }

        // 🔹 Step 1: old current = false
        locationRepository.findByUserIdAndCurrentTrue(userId)
                .ifPresent(loc -> loc.setCurrent(false));

        // 🔹 Step 2: new current = true
        target.setCurrent(true);
        locationRepository.save(target);

        // 🔹 Step 3: update profile
        profile.setCurrentCity(target.getCity());
        profile.setCurrentState(target.getState());
        profile.setCurrentCountry(target.getCountry());
        profile.setCurrentLat(target.getLat());
        profile.setCurrentLng(target.getLng());

        userProfileRepository.save(profile);

        return "Location switched successfully";
    }
}
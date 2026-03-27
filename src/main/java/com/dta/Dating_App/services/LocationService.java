package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.LocationRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserLocation;
import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserLocationRepository;
import com.dta.Dating_App.repository.UserProfileRepository;
import com.dta.Dating_App.repository.UserRepository;
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

    // ✅ Get location history
    public List<UserLocation> getLocationHistory(Long userId) {
        return locationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // ✅ Add new location
    public String addNewLocation(LocationRequest request){

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // old current location false
        locationRepository.findByUserIdAndCurrentTrue(request.getUserId())
                .ifPresent(loc ->{
                    loc.setCurrent(false);
                    locationRepository.save(loc);
                });

        // create new location
        UserLocation location = UserLocation.builder()
                .userId(request.getUserId())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .lat(request.getLat())
                .lng(request.getLng())
                .current(true)
                .createdAt(LocalDateTime.now())
                .build();

        locationRepository.save(location);

        // ✅ FIX: update in profile (not user)
        profile.setCurrentCity(request.getCity());
        profile.setCurrentState(request.getState());
        profile.setCurrentCountry(request.getCountry());
        profile.setCurrentLat(request.getLat());
        profile.setCurrentLng(request.getLng());

        userProfileRepository.save(profile);

        return "New location added & set as current";
    }

    // ✅ Switch location
    public String switchLocation (Long userId, Long locationId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        UserLocation target = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        if(!target.getUserId().equals(userId)){
            throw new RuntimeException("This location does not belong to user");
        }

        // old current = false
        locationRepository.findByUserIdAndCurrentTrue(userId)
                .ifPresent(loc -> {
                    loc.setCurrent(false);
                    locationRepository.save(loc);
                });

        // new current = true
        target.setCurrent(true);
        locationRepository.save(target);

        // ✅ FIX: update profile
        profile.setCurrentCity(target.getCity());
        profile.setCurrentState(target.getState());
        profile.setCurrentCountry(target.getCountry());
        profile.setCurrentLat(target.getLat());
        profile.setCurrentLng(target.getLng());

        userProfileRepository.save(profile);

        return "Location switched successfully";
    }
}
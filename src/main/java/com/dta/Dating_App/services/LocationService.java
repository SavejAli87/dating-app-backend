package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.LocationRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserLocation;
import com.dta.Dating_App.repository.UserLocationRepository;
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

    //Get current location

    public UserLocation getCurrentLocation(Long userId){
        return locationRepository.findByUserIdAndCurrentTrue(userId)
                .orElseThrow(() -> new RuntimeException("Current location not set"));
    }

    //  Get Location History
    public List<UserLocation> getLocationHistory(Long userId) {
        return locationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // Add new location ( also set current)
    public String addNewLocation(LocationRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // old current location false
        locationRepository.findByUserIdAndCurrentTrue(request.getUserId())
                .ifPresent(loc ->{
                    loc.setCurrent(false);
                    locationRepository.save(loc);
                });

        // create new location entry
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

        //update user current fields
        user.setCurrentCity(request.getCity());
        user.setCurrentState(request.getState());
        user.setCurrentCountry(request.getCountry());
        user.setCurrentLat(request.getLat());
        user.setCurrentLng(request.getLng());

        userRepository.save(user);

        return "New location added & set as current ";
    }

    // Switch back to previous location by locationId
    public String switchLocation (Long userId, Long locationId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found "));

        UserLocation target = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        if(!target.getUserId().equals(userId)){
            throw  new RuntimeException("This location does not belong to user ");
        }

        //old current = false

        locationRepository.findByUserIdAndCurrentTrue(userId)
                .ifPresent(loc -> {
                    loc.setCurrent(false);
                    locationRepository.save(loc);
                });

        // Set target current = true

        target.setCurrent(true);
        locationRepository.save(target);

        // update user current fields
        user.setCurrentCity(target.getCity());
        user.setCurrentState(target.getState());
        user.setCurrentCountry(target.getCountry());
        user.setCurrentLat(target.getLat());
        user.setCurrentLng(target.getLng());
        userRepository.save(user);

        return "Location switched successfully";

    }
}

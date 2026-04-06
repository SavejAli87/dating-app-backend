package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    // ✅ Get all locations of a user
    List<UserLocation> findByUserIdOrderByCreatedAtDesc(Long userId);

    // ✅ Get current location
    Optional<UserLocation> findByUserIdAndCurrentTrue(Long userId);

    // ✅ Check duplicate location
    boolean existsByUserIdAndCityAndStateAndCountry(
            Long userId,
            String city,
            String state,
            String country
    );
}
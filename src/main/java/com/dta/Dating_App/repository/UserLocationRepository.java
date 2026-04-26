package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    //  Get all locations of a user
    List<UserLocation> findByUserIdOrderByCreatedAtDesc(Long userId);

    //  Get current location
    Optional<UserLocation> findByUserIdAndCurrentTrue(Long userId);

    Optional<UserLocation> findByUser_UserIdAndCurrentTrue(String userId);

    List<UserLocation> findByUser_UserIdOrderByCreatedAtDesc(String userId);

    //  Check duplicate location
    boolean existsByUserIdAndCityAndStateAndCountry(
            Long userId,
            String city,
            String state,
            String country
    );

    @Query(value = """
SELECT * FROM (
    SELECT 
        ul.user_id AS userId,
        ul.city AS city,
        (
            6371 * acos(
                cos(radians(:lat)) *
                cos(radians(ul.lat)) *
                cos(radians(ul.lng) - radians(:lng)) +
                sin(radians(:lat)) *
                sin(radians(ul.lat))
            )
        ) AS distance
    FROM user_location ul
    WHERE ul.current = true
    AND ul.user_id != :userId
) AS temp
WHERE temp.distance <= :radius
ORDER BY temp.distance ASC
""", nativeQuery = true)
    List<Object[]> findNearbyUsers(
            @Param("userId") String userId,
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("radius") Double radius
    );
}
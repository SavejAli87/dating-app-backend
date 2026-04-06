package com.dta.Dating_App.repository;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);

    boolean existsByDisplayName(String displayName);



    Optional<UserProfile> findByUser_UserId(String userId);

    List<UserProfile> findByGender(String gender);

    List<UserProfile> findByGenderAndUser_UserIdNot(String gender, String userId);




}
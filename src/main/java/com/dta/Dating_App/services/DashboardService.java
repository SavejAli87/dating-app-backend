package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserProfileRepository userProfileRepository;

    // 🟢 Online users
    public Page<UserProfile> getOnlineUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by("lastSeen").descending());

        return userProfileRepository.findByOnlineTrue(pageable);
    }

    //  Recent joined users
    public Page<UserProfile> getRecentJoinedUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by("createdAt").descending());

        return userProfileRepository.findAll(pageable);
    }
}
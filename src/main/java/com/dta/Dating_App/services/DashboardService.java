package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;

    // online user

    public Page<User> getOnlineUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastSeen").descending());
        return userRepository.findAll((root, query, cb) ->
                cb.equal(root.get("online"), true), pageable);
    }

    // Recent joined users
    public Page<User> getRecentJoinedUsers(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        return userRepository.findAll(pageable);
    }
}

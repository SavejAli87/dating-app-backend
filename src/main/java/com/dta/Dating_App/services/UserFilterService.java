package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.UserFilterRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserProfileRepository;
import com.dta.Dating_App.repository.UserRepository;
import com.dta.Dating_App.specification.UserSpecifications;
import com.dta.Dating_App.utils.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFilterService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public Page<User> filterUsers(UserFilterRequest request) {

        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize()
                //  "online" removed (now inside profile)
        );

        Page<User> users = userRepository.findAll(
                UserSpecifications.filterUsers(request),
                pageable
        );

        //  Worldwide → no distance filter
        if (Boolean.TRUE.equals(request.getWorldwide())) {
            return users;
        }

        //  Distance filter
        if (request.getMaxDistanceKm() != null && request.getUserId() != null) {

            UserProfile myProfile = userProfileRepository.findByUserId(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("Profile not found"));

            if (myProfile.getCurrentLat() == null || myProfile.getCurrentLng() == null) {
                throw new RuntimeException("Your location not set");
            }

            List<User> filtered = users.getContent().stream()
                    .filter(u -> {
                        UserProfile p = userProfileRepository.findByUserId(u.getId()).orElse(null);
                        return p != null && p.getCurrentLat() != null && p.getCurrentLng() != null;
                    })
                    .filter(u -> {
                        UserProfile p = userProfileRepository.findByUserId(u.getId()).orElse(null);

                        return DistanceUtil.distanceKm(
                                myProfile.getCurrentLat(), myProfile.getCurrentLng(),
                                p.getCurrentLat(), p.getCurrentLng()
                        ) <= request.getMaxDistanceKm();
                    })
                    .collect(Collectors.toList());

            return new PageImpl<>(filtered, pageable, filtered.size());
        }

        return users;
    }
}